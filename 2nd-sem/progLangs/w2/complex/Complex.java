package Complex;

public class Complex {
    private double re;
    private double im;

    public Complex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    /**
     * Returns a new Complex number representing the conjugate: a - bi
     */
    public Complex conjugate() {
        return new Complex(re, -im);
    }

    /**
     * Returns a new Complex number representing the reciprocal: 1 / (a + bi)
     * Formula: (a / (a² + b²)) - (b / (a² + b²))i
     */
    public Complex reciprocate() {
        double denominator = (re * re) + (im * im);
        // It's good practice to check if denominator is 0 to avoid Division by Zero
        return new Complex(re / denominator, -im / denominator);
    }

    /**
     * Returns a new Complex number representing the division of this by c
     * Following the formula: (a + bi) / (c + di)
     */
    public Complex div(Complex c) {
        double denominator = (c.re * c.re) + (c.im * c.im);
        double newRe = (re * c.re + im * c.im) / denominator;
        double newIm = (im * c.re - re * c.im) / denominator;
        return new Complex(newRe, newIm);
    }

    public double calcAbs() {
        return Math.sqrt(im * im + re * re);
    }

    public Complex calcMul(Complex c) {
        double newRe = (re * c.re) - (im * c.im);
        double newIm = (re * c.im) + (im * c.re);
        return new Complex(newRe, newIm);
    }

    public Complex calcAdd(Complex c) {
        return new Complex(re + c.re, im + c.im);
    }

    public Complex calcSub(Complex c) {
        Complex negated = new Complex(-c.re, -c.im);
        return calcAdd(negated);
    }

    public String getText() {
        if (im == 0) {
            return "%.1f".formatted(re);
        }
        // Use Math.abs for the imaginary part in the string so we don't get "+ -3.0i"
        char sign = (im > 0) ? '+' : '-';
        return "%.1f %c %.1fi".formatted(re, sign, Math.abs(im));
    }
}