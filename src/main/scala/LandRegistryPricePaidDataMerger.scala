import java.nio.file.{Files, Paths}

import sys.process._
import java.net.URL
import java.io._

import com.github.tototoshi.csv._
import org.joda.time.DateTime

object LandRegistryPricePaidDataMerger extends App {

  val CurrentCsv = "LandRegistryPricePaidData.csv"
  val MonthlyCsv = "Monthly.csv"
  val CompleteUrl = "http://prod.publicdata.landregistry.gov.uk.s3-website-eu-west-1.amazonaws.com/pp-complete.csv"
  val MonthlyUrl = "http://prod.publicdata.landregistry.gov.uk.s3-website-eu-west-1.amazonaws.com/pp-monthly-update-new-version.csv"

  // Get the big one ? (if CurrentCsv is not there)
  if (shouldGetComplete(CurrentCsv)) download(CompleteUrl, CurrentCsv)

//  // Get and merge a monthly one ? (based on CurrentCsv update time)
//  if (shouldGetMonthly(MonthlyCsv)) download(MonthlyUrl, MonthlyCsv)

//  mergeCsv(new File(CurrentCsv), new File(MonthlyCsv))

  // Daemon ? and run it again (next month)
  // ..
  // TODO

  def download(fromUrl: String, toFile: String): Unit = {
    println("Downloading " + toFile + " from " + fromUrl)
    new URL(fromUrl) #> new File(toFile) !!;
    println(toFile + " downloaded")
  }

  /**
    * In order to get the complete file, currentCsv should not exist
    * @param currentCsv
    * @return
    */
  def shouldGetComplete(currentCsv: String): Boolean = !Files.exists(Paths.get(currentCsv))


  /**
    * In order to get a monthy file, currentCsv modification date must be older than 1 month
    * @param currentCsv
    * @return
    */
  def shouldGetMonthly(currentCsv: String): Boolean = {
    val file = new File(currentCsv)
    val now = DateTime.now
    val firstDayOfThisMonth = new DateTime(now.year.get, now.monthOfYear.get, 1, 0, 0)

    file.lastModified < firstDayOfThisMonth.getMillis
  }

  def mergeCsv(currentCsv: File, inputCsv: File) {
    println("merging " + inputCsv + " into " + currentCsv)

    // read currentCsv
    println("Reading " + currentCsv)
    val readerCurrentCsv = CSVReader.open(currentCsv)
    val currentList = readerCurrentCsv.all.map(line =>  LandRegistryPricePaidDataItem(line))
    readerCurrentCsv.close()

    // read inputCsv
    println("Reading " + inputCsv)
    val readerInputCsv = CSVReader.open(inputCsv)
    val inputList = readerInputCsv.all.map(line =>  LandRegistryPricePaidDataItem(line))
    readerInputCsv.close()

    // merge
    println("Merging")
    val merged = new LandRegistryPricePaidData(currentList).merge(inputList)

    // save to currentCsv
    val writer = new PrintWriter(currentCsv)
    println("Save on "+ currentCsv)
    merged.paidDatas.foreach(d => {
      writer.write(d._2.toString() + "\n")
    })
    writer.close()

    println(inputCsv + "merged into " + currentCsv)
  }
}