package _02openclose;

//进行扩展
class DiscountCar extends Car{
        @Override
        public void setPrice(float price) {
            super.setPrice(price*0.8f);
        }
}
