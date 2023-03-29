package _02openclose;

class Car {
        private String band;
        private String color;
        private float price;

        public String getBand() {
            return band;
        }

        public void setBand(String band) {
            this.band = band;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "Car{" +
                    "band='" + band + '\'' +
                    ", color='" + color + '\'' +
                    ", price=" + price +
                    '}';
        }
}
