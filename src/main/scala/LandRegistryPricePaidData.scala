import scala.collection.immutable.TreeMap

package object PaidDatas {
  type PaidDatas = TreeMap[String, LandRegistryPricePaidDataItem]
}


class LandRegistryPricePaidData(val paidDatas: PaidDatas.PaidDatas ) {

  def this(ppds: List[LandRegistryPricePaidDataItem] = List() ) {
    this(ppds.map(t => t.transactionUniqueIdentifier -> t)(collection.breakOut) : PaidDatas.PaidDatas)
  } 

  
  def merge(ppds: List[LandRegistryPricePaidDataItem]): LandRegistryPricePaidData = { 
    val newListOfPpds = ppds.foldLeft(paidDatas)((z, t) => 
      t.recordStatus match {
        case 'D' => z - t.transactionUniqueIdentifier
        case _   => z + (t.transactionUniqueIdentifier -> t)
      }
    ) 
     
    new LandRegistryPricePaidData(newListOfPpds)
  }
}
