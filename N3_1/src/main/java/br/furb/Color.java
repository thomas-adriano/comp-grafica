package br.furb;

public class Color {

	private final float r;
	private final float g;
	private final float b;
	
	public Color(float r, float g, float b) {
		super();
		this.r = r;
		this.g = g;
		this.b = b;
	}

	public float getR() {
		return r;
	}

	public float getG() {
		return g;
	}

	public float getB() {
		return b;
	}

	@Override
	public String toString() {
		return "Color [r=" + r + ", g=" + g + ", b=" + b + "]";
	}
	
	
	
	
}
