import java.nio.file.{Paths, Files}
import sys.process._
import java.net.URL
import java.io._

import com.github.tototoshi.csv._

object LandRegistryPricePaidDataMerger extends App {

  val CurrentCsv = "LandRegistryPricePaidData.csv"
  val InputCsv = "Input.csv"
  val CompleteUrl = "http://prod.publicdata.landregistry.gov.uk.s3-website-eu-west-1.amazonaws.com/pp-complete.csv"
  val MonthlyUrl = "http://prod.publicdata.landregistry.gov.uk.s3-website-eu-west-1.amazonaws.com/pp-monthly-update.csv"

  // Get the big one ? (if CurrentCsv is not there)
  if (shouldGetComplete(CurrentCsv)) download(CompleteUrl, CurrentCsv)

  // Get and merge a monthly one ? (based on CurrentCsv update time)
  if (shouldGetMonthly(CurrentCsv)) download(MonthlyUrl, CurrentCsv)

  // Daemon ? and run it again (next month)
  // ..
  // TODO

  def download(fromUrl: String, toFile: String): Unit = {
    new URL(fromUrl) #> new File(toFile) !!
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
    // TODO
    false
  }

  def mergeCsv(currentCsv: File, inputCsv: File) {
    
    // read currentCsv
    val readerCurrentCsv = CSVReader.open(currentCsv)
    val currentList = readerCurrentCsv.all.map(line =>  LandRegistryPricePaidDataItem(line))
    readerCurrentCsv.close()

    // read inputCsv
    val readerInputCsv = CSVReader.open(inputCsv)
    val inputList = readerInputCsv.all.map(line =>  LandRegistryPricePaidDataItem(line))
    readerInputCsv.close()

    // merge
    val merged = new LandRegistryPricePaidData(currentList).merge(inputList)

    // save to currentCsv
    val writer = new PrintWriter(currentCsv)
    println("Save on "+ currentCsv)
    merged.paidDatas.foreach(d => {
      writer.write(d._2.toString() + "\n")
    })
    writer.close()
    
  }
}