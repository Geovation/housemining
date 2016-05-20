/**
see https://www.gov.uk/government/statistical-data-sets/price-paid-data-downloads
https://www.gov.uk/guidance/about-the-price-paid-data
*/ 
case class LandRegistryPricePaidDataItem(
    transactionUniqueIdentifier: String, 
    price: String, 
    dateOfTransfer: String,
    postcode: String,
    propertyType: String,
    oldNew: String,
    duration: String,
    PAON: String,
    SAON: String,
    street: String,
    locality: String,
    townCity: String,
    District: String,
    County: String,
    PPDCategoryType: String,
    recordStatus: String) {

  override def toString():String = {
    this.getClass.getDeclaredFields.foldLeft("")((str,cell) => {
      cell.setAccessible(true)

      val rtn = "\""+ cell.get(this) +"\""
      if (str.length>0) str + "," + rtn
      else rtn
    })
  }
}

object LandRegistryPricePaidDataItem {
  def apply(cols: List[String]): LandRegistryPricePaidDataItem = {
    new LandRegistryPricePaidDataItem(cols(0),cols(1),cols(2),cols(3),cols(4),cols(5),cols(6),cols(7),cols(8),cols(9),cols(10),cols(11),cols(12),cols(13),cols(14),cols(15))
  }
}
