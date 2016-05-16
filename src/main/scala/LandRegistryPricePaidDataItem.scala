/**
see https://www.gov.uk/government/statistical-data-sets/price-paid-data-downloads
https://www.gov.uk/guidance/about-the-price-paid-data
*/
class LandRegistryPricePaidDataItem(val transactionUniqueIdentifier: String, val price: Int, val recordStatus: Char = 'A') {
  //var dateOfTransfer: java.util.Date = _
  //var postcode: String = _
  // more from https://www.gov.uk/guidance/about-the-price-paid-data
  
  
}
