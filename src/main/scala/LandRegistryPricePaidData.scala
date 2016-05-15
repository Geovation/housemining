import scala.collection.immutable.TreeMap

class LandRegistryPricePaidData(private val ppds: List[LandRegistryPricePaidDataItem] = List() ) {

  type PaidDatas = TreeMap[String, LandRegistryPricePaidDataItem]

  val paidDatas = ppds.map(t => t.transactionUniqueIdentifier -> t)(collection.breakOut) : PaidDatas

  def merge(ppds: List[LandRegistryPricePaidDataItem]): LandRegistryPricePaidData = {
    new LandRegistryPricePaidData(ppds)
  }
}
