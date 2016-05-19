import org.scalatest._
import org.scalatest.matchers.ShouldMatchers

// see https://github.com/tototoshi/scala-csv/releases
import com.github.tototoshi.csv._

import java.io._



class LandRegistryPricePaidDataMergerSpec extends FlatSpec with ShouldMatchers {
    "csv file" should "be merged with empty one" in {
      
      // create temp dir 
      val destDir = System.getProperty("java.io.tmpdir")
      val destCsvFile = new File(destDir, "dest.csv" )
      val dataCsv = getClass.getResource("/data.csv")
      val dataCsvFile = new File(dataCsv.getFile)
        
      val writer = new PrintWriter(destCsvFile)
      writer.close()

      // merge with data.csv
      LandRegistryPricePaidDataMerger.mergeCsv(destCsvFile, dataCsvFile)      
      
      // compare the two files line by line
      val readerData = CSVReader.open(dataCsvFile)
      val readerDest = CSVReader.open(destCsvFile)

      val mapData = readerData.all.map(l => l(0) -> l).toMap
      val mapDest = readerDest.all.map(l => l(0) -> l).toMap
      readerData.close
      readerDest.close

      mapData should equal (mapDest)
    }
}