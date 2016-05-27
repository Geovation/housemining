import com.github.nscala_time.time.Imports._

import org.scalatest._
import org.scalatest.matchers.ShouldMatchers

import com.github.tototoshi.csv._

import java.io._
import java.nio.file._


class LandRegistryPricePaidDataMergerSpec extends FlatSpec with ShouldMatchers {
    "csv file" should "be merged with empty one" in {
      
      // create temp dir 
      val destDir = System.getProperty("java.io.tmpdir")

      // files
      val destCsvFile = new File(destDir, "dest.csv" )
      val dataCsvFile = new File(getClass.getResource("/data.csv").getFile)
        
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

    "csv files" should "be merged" in {
      // create temp dir
      val destDir = System.getProperty("java.io.tmpdir")

      // files
      val destCsvFile = new File(destDir, "dest.csv" )
      val dataCsvFile = new File(getClass.getResource("/data.csv").getFile)
      val data2CsvFile = new File(getClass.getResource("/data-2.csv").getFile)
      val dataMergedCsvFile = new File(getClass.getResource("/data-merged.csv").getFile)


      val writer = new PrintWriter(destCsvFile)
      writer.close()

      // merge with data.csv
      LandRegistryPricePaidDataMerger.mergeCsv(destCsvFile, dataCsvFile)

      // merge with data-2.csv
      LandRegistryPricePaidDataMerger.mergeCsv(destCsvFile, data2CsvFile)

      // compare the two files line by line
      val readerData = CSVReader.open(dataMergedCsvFile)
      val readerDest = CSVReader.open(destCsvFile)

      val mapData = readerData.all.map(l => l(0) -> l).toMap
      val mapDest = readerDest.all.map(l => l(0) -> l).toMap
      readerData.close
      readerDest.close

      mapData should equal (mapDest)
    }

    "shouldGetComplete" should "return false if the file exist" in {
      val path = getClass.getResource("/data.csv").getPath
      LandRegistryPricePaidDataMerger.shouldGetComplete(path) should be (false)
    }

    "shouldGetComplete" should "return true if the file does not exist" in {
      val path = "unexisting.csv"
      LandRegistryPricePaidDataMerger.shouldGetComplete(path) should be (true)
    }

    "shouldGetMonthly" should "return true if the file does not exist" in {
      val fileName = "unexisting.csv"
      LandRegistryPricePaidDataMerger.shouldGetMonthly(fileName) should be (true)
    }

    "shouldGetMonthly" should "return true if the file exist but it is from previous month" in {
      // create temp dir
      val destDir = System.getProperty("java.io.tmpdir")

      // create file
      val path = Paths.get(destDir, "file.csv")
      if (Files.exists(path)) Files.delete(path)
      val file = Files.createFile(path).toFile

      // change last modified time to the last day of the previous month
      val now = DateTime.now
      val firstDayOfThisMonth = new DateTime(now.year.get, now.monthOfYear.get, 1, 0, 0)
      val lastDayPreviousMonth = firstDayOfThisMonth - 1.day
      file.setLastModified(lastDayPreviousMonth.getMillis)

      LandRegistryPricePaidDataMerger.shouldGetMonthly(path.toString) should be (true)
    }


    "shouldGetMonthly" should "return false if the file exist but it is from this month" in {
      // create temp dir
      val destDir = System.getProperty("java.io.tmpdir")

      // create file
      val path = Paths.get(destDir, "file.csv")
      if (Files.exists(path)) Files.delete(path)
      val file = Files.createFile(path).toFile

      // change last modified time to the last day of the previous month
      val now = DateTime.now
      val firstDayOfThisMonth = new DateTime(now.year.get, now.monthOfYear.get, 1, 0, 0)
      file.setLastModified(firstDayOfThisMonth.getMillis)

      LandRegistryPricePaidDataMerger.shouldGetMonthly(path.toString) should be (false)    }
  }