package progfun

import fr.esgi.al.funprog.{Exporter, Lawn, Mower, Orientation, Position}

import org.scalatest.funsuite.AnyFunSuite

class ExporterSpec extends AnyFunSuite {

  test("export a new CSV") {
    val mowers = List(
      Some(
        Mower(
          Position(1, 2, Orientation.N),
          "GAGAGAGAA",
          Position(1, 3, Orientation.N)
        )
      ),
      Some(
        Mower(
          Position(3, 3, Orientation.E),
          "AADAADADDA",
          Position(5, 1, Orientation.E)
        )
      )
    )
    val current = Exporter.exportToCsv(mowers)
    val expected =
      "numéro;début_x;début_y;début_direction;fin_x;fin_y;fin_direction;instructions\n1;1;2;N;1;3;N;GAGAGAGAA\n2;3;3;E;5;1;E;AADAADADDA"
    assert(current == expected)
  }

  test("export a new JSON") {
    val mowers = List(
      Some(
        Mower(
          Position(1, 2, Orientation.N),
          "GAGAGAGAA",
          Position(1, 3, Orientation.N)
        )
      ),
      Some(
        Mower(
          Position(3, 3, Orientation.E),
          "AADAADADDA",
          Position(5, 1, Orientation.E)
        )
      )
    )
    val current = Exporter.exportToJson(mowers, Lawn(5, 5))
    val expected =
      "{\n  \"limite\" : {\n    \"x\" : 5,\n    \"y\" : 5\n  },\n  \"tondeuses\" : [ {\n    \"debut\" : {\n      \"point\" : {\n        \"x\" : 1,\n        \"y\" : 2\n      },\n      \"direction\" : \"N\"\n    },\n    \"instructions\" : [ \"G\", \"A\", \"G\", \"A\", \"G\", \"A\", \"G\", \"A\", \"A\" ],\n    \"fin\" : {\n      \"point\" : {\n        \"x\" : 1,\n        \"y\" : 3\n      },\n      \"direction\" : \"N\"\n    }\n  }, {\n    \"debut\" : {\n      \"point\" : {\n        \"x\" : 3,\n        \"y\" : 3\n      },\n      \"direction\" : \"E\"\n    },\n    \"instructions\" : [ \"A\", \"A\", \"D\", \"A\", \"A\", \"D\", \"A\", \"D\", \"D\", \"A\" ],\n    \"fin\" : {\n      \"point\" : {\n        \"x\" : 5,\n        \"y\" : 1\n      },\n      \"direction\" : \"E\"\n    }\n  } ]\n}"

    assert(current == expected.replace("\n", System.lineSeparator))
  }

  test("export a new YAML") {
    val mowers = List(
      Some(
        Mower(
          Position(1, 2, Orientation.N),
          "GAGAGAGAA",
          Position(1, 3, Orientation.N)
        )
      ),
      Some(
        Mower(
          Position(3, 3, Orientation.E),
          "AADAADADDA",
          Position(5, 1, Orientation.E)
        )
      )
    )
    val current = Exporter.exportToYaml(mowers, Lawn(5, 5))
    val expected =
      "limite:\n  x: 5\n  y: 5\ntondeuses:\n- debut:\n    point:\n      x: 1\n      y: 2\n    direction: N\n  instructions: \n  - G\n  - A\n  - G\n  - A\n  - G\n  - A\n  - G\n  - A\n  - A\n  fin:\n    point:\n      x: 1\n      y: 3\n    direction: N\n- debut:\n    point:\n      x: 3\n      y: 3\n    direction: E\n  instructions: \n  - A\n  - A\n  - D\n  - A\n  - A\n  - D\n  - A\n  - D\n  - D\n  - A\n  fin:\n    point:\n      x: 5\n      y: 1\n    direction: E"
    assert(current.replace("\n", "") == expected.replace("\n", ""))
  }

  test("write a file") {
    val lines =
      Exporter.writeToFile("Hello world !", "src/test/resources/test.txt")
    assert(lines.isSuccess)
  }

  test("not write a file") {
    val lines = Exporter.writeToFile("Hello world !", "unkown/test.txt")
    assert(!lines.isSuccess)
  }

}
