package progfun

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.scalatest.matchers.should.Matchers.not.be
class ExporterSpec extends AnyFlatSpec {

  it should "export a new CSV file" in {
    val mowers = List(
      Some(Mower(Position(1, 2, Orientation.N), "GAGAGAGAA", Position(1, 3, Orientation.N))),
      Some(Mower(Position(3, 3, Orientation.E), "AADAADADDA", Position(5, 1, Orientation.E)))
    )
    val outputFile = "src/test/resources/output.csv"
    Exporter.exportToCsv(mowers, outputFile)
    val lines = Parser.readFile(outputFile)
    lines should be(
      List(
        "1 2 N",
        "GAGAGAGAA",
        "1 3 N",
        "3 3 E",
        "AADAADADDA",
        "5 1 E"
      )
    )
  }

  it should "export a new JSON file" in {
    val mowers = List(
      Some(Mower(Position(1, 2, Orientation.N), "GAGAGAGAA", Position(1, 3, Orientation.N))),
      Some(Mower(Position(3, 3, Orientation.E), "AADAADADDA", Position(5, 1, Orientation.E)))
    )
    val outputFile = "src/test/resources/output.json"
    Exporter.exportToJson(mowers, Lawn(5,5), outputFile)
    val lines = Parser.readFile(outputFile)
    lines should be(
      List(
        "1 2 N",
        "GAGAGAGAA",
        "1 3 N",
        "3 3 E",
        "AADAADADDA",
        "5 1 E"
      )
    )
  }

}
