import scala.io.Source

import java.io._

import com.github.tototoshi.csv._


object LandRegistryPricePaidDataMerger {
//  val CurrentCsv = "LandRegistryPricePaidData.csv"
//  val InputCsv = "Input.csv"
//  val CompleteUrl = "http://prod.publicdata.landregistry.gov.uk.s3-website-eu-west-1.amazonaws.com/pp-complete.csv"
//  val MonthlyUrl = "http://prod.publicdata.landregistry.gov.uk.s3-website-eu-west-1.amazonaws.com/pp-monthly-update.txt"
  
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