/**
see https://www.gov.uk/government/statistical-data-sets/price-paid-data-downloads
https://www.gov.uk/guidance/about-the-price-paid-data
*/
import java.util.Date
  
class LandRegistryPricePaidDataItem(
    val transactionUniqueIdentifier: String, 
    val price: Int, 
    val dateOfTransfer: Date,
    val postcode: String,
    val propertyType: Char,
    val oldNew: Char,
    val duration: Char,
    val PAON: String,
    val SAON: String,
    val street: String,
    val locality: String,
    val townCity: String,
    val District: String,
    val County: String,
    val PPDCategoryType: Char,
    val recordStatus: Char) { 
}
