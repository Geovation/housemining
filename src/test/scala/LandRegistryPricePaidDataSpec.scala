import org.scalatest._
import org.scalatest.matchers.ShouldMatchers

class LandRegistryPricePaidDataSpec extends FlatSpec with ShouldMatchers {
  "paidDatas of empty constructor" should "have zero elements" in {
    (new LandRegistryPricePaidData()).paidDatas.isEmpty should be === true
  }

  "merge with an empty list" should "have zero elements" in {
    (new LandRegistryPricePaidData()).merge(List()).paidDatas.isEmpty should be === true
  }

  "merge a list of one element" should "return a no empty map" in {
    val paidDataItem = new LandRegistryPricePaidDataItem()
    new LandRegistryPricePaidData().merge(List(paidDataItem)).paidDatas.isEmpty should be === false
  }
}