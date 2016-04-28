

public class P4D {

    private double x; /// valor X.
    private double y; /// valor Y.
    private double z; /// valor Z.
    private double w; /// valor W.

    /// Cria o ponto (0,0,0,1).
    public P4D() {
        this(0, 0, 0, 1);
    }

    /// Cria o ponto (0,0,0,1).
    public P4D(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public P4D inverterSinal(P4D pto) {
        pto.atribuirX(pto.obterX() * -1);
        pto.atribuirY(pto.obterY() * -1);
        pto.atribuirZ(pto.obterZ() * -1);
        return pto;
    }

    /// Obter valor X do ponto.
    public double obterX() {
        return x;
    }

    /// Obter valor Y do ponto.
    public double obterY() {
        return y;
    }

    /// Obter valor Z do ponto.
    public double obterZ() {
        return z;
    }

    /// Obter valor W do ponto.
    public double obterW() {
        return w;
    }

    /// Atribuir valor X do ponto.
    public void atribuirX(double x) {
        this.x = x;
    }

    /// Atribuir valor Y do ponto.
    public void atribuirY(double y) {
        this.y = y;
    }

    /// Atribuir valor Z do ponto.
    public void atribuirZ(double z) {
        this.z = z;
    }

	/// Atribuir valor W do ponto.
//	public void AtribuirW(double w) {
//		this.w = w;
//	}
}
