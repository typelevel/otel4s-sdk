/*
 * Copyright 2023 Typelevel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.typelevel.otel4s.sdk.scalacheck

import cats.data.NonEmptyVector
import org.scalacheck.Arbitrary
import org.scalacheck.Gen
import org.typelevel.otel4s.{AnyValue, Attribute, AttributeKey, Attributes}
import org.typelevel.otel4s.sdk.TelemetryResource
import org.typelevel.otel4s.sdk.common.InstrumentationScope
import org.typelevel.otel4s.sdk.context.TraceContext
import org.typelevel.otel4s.sdk.data.LimitedData
import scodec.bits.ByteVector

import scala.concurrent.duration._

trait Gens {

  def nonEmptyVector[A](gen: Gen[A]): Gen[NonEmptyVector[A]] =
    for {
      head <- gen
      tail <- Gen.nonEmptyContainerOf[Vector, A](gen)
    } yield NonEmptyVector(head, tail)

  val nonEmptyString: Gen[String] =
    for {
      id <- Gen.identifier
      str <- Gen.stringOfN(5, Gen.alphaNumChar)
    } yield id ++ str

  val nonZeroLong: Gen[Long] =
    Gen.oneOf(
      Gen.choose(Long.MinValue, -1L),
      Gen.choose(1L, Long.MaxValue)
    )

  val timestamp: Gen[FiniteDuration] =
    Gen.chooseNum(1L, Long.MaxValue).map(_.nanos)

  val attribute: Gen[Attribute[_]] = {
    implicit val stringArb: Arbitrary[String] =
      Arbitrary(nonEmptyString)

    implicit def seqArb[A: Arbitrary]: Arbitrary[Seq[A]] =
      Arbitrary(Gen.nonEmptyListOf(Arbitrary.arbitrary[A]))

    def attribute[A: AttributeKey.KeySelect: Arbitrary]: Gen[Attribute[A]] =
      for {
        key <- nonEmptyString
        value <- Arbitrary.arbitrary[A]
      } yield Attribute(key, value)

    val string: Gen[Attribute[String]] = attribute[String]
    val boolean: Gen[Attribute[Boolean]] = attribute[Boolean]
    val long: Gen[Attribute[Long]] = attribute[Long]
    val double: Gen[Attribute[Double]] = attribute[Double]

    val stringSeq: Gen[Attribute[Seq[String]]] = attribute[Seq[String]]
    val booleanSeq: Gen[Attribute[Seq[Boolean]]] = attribute[Seq[Boolean]]
    val longSeq: Gen[Attribute[Seq[Long]]] = attribute[Seq[Long]]
    val doubleSeq: Gen[Attribute[Seq[Double]]] = attribute[Seq[Double]]

    Gen.oneOf(
      boolean,
      string,
      long,
      double,
      stringSeq,
      booleanSeq,
      longSeq,
      doubleSeq
    )
  }

  val attributes: Gen[Attributes] =
    for {
      attributes <- Gen.listOf(attribute)
    } yield attributes.to(Attributes)

  def attributes(n: Int): Gen[Attributes] =
    for {
      attributes <- Gen.listOfN(n, attribute)
    } yield attributes.to(Attributes)

  val anyValue: Gen[AnyValue] = {
    val string = Gen.alphaNumStr.map(AnyValue.string)
    val boolean = Arbitrary.arbitrary[Boolean].map(AnyValue.boolean)
    val long = Gen.long.map(AnyValue.long)
    val double = Gen.double.map(AnyValue.double)
    val byteArray = Gen.listOf(Gen.choose(Byte.MinValue, Byte.MaxValue)).map(_.toArray).map(AnyValue.bytes)
    val emptyValue = Gen.const(AnyValue.empty)

    val primitives = Gen.oneOf(string, boolean, long, double, byteArray, emptyValue)

    def array: Gen[AnyValue] =
      for {
        values <- Gen.listOf(primitives)
      } yield AnyValue.seq(values)

    def map: Gen[AnyValue] =
      for {
        values <- Gen.listOf(Gen.zip(Gens.nonEmptyString, primitives))
      } yield AnyValue.map(values.toMap)

    Gen.oneOf(primitives, array, map)
  }

  val telemetryResource: Gen[TelemetryResource] =
    for {
      attributes <- Gens.attributes
      schemaUrl <- Gen.option(nonEmptyString)
    } yield TelemetryResource(attributes, schemaUrl)

  val instrumentationScope: Gen[InstrumentationScope] =
    for {
      name <- nonEmptyString
      version <- Gen.option(nonEmptyString)
      schemaUrl <- Gen.option(nonEmptyString)
      attributes <- Gens.attributes
    } yield InstrumentationScope(name, version, schemaUrl, attributes)

  val limitedAttributes: Gen[LimitedData[Attribute[_], Attributes]] =
    for {
      attributes <- Gens.nonEmptyVector(Gens.attribute)
      extraAttributes <- Gens.nonEmptyVector(Gens.attribute)
      valueLengthLimit <- Gen.posNum[Int]
    } yield LimitedData
      .attributes(attributes.length, valueLengthLimit)
      .appendAll((attributes ++: extraAttributes).toVector.to(Attributes))

  val traceContext: Gen[TraceContext] =
    for {
      traceId <- Gen.zip(Gen.long, nonZeroLong)
      spanId <- nonZeroLong
      sampled <- Arbitrary.arbitrary[Boolean]
    } yield TraceContext(
      ByteVector.fromLong(traceId._1, 8) ++ ByteVector.fromLong(traceId._2, 8),
      ByteVector.fromLong(spanId, 8),
      sampled
    )

}

object Gens extends Gens
