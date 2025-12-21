package _10builder;

//产品类Meal（套餐类）
public class Meal
{
	private String food;
	private String drink;

	public String getFood() {
		return this.food;
	}

	public void setFood(final String food) {
		this.food = food;
	}

	public String getDrink() {
		return this.drink;
	}

	public void setDrink(final String drink) {
		this.drink = drink;
	}

	@Override
	public String toString() {
		return "Meal{" +
				"food='" + food + '\'' +
				", drink='" + drink + '\'' +
				'}';
	}
}
