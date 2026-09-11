const [f, a, b, c, d, e] = process.argv.slice(2).map(Number)

function convert(f) {
    return 5 / 9 * (f - 32)
}

function isTriangle(a, b, c) {
    return a + b + c > 2 * Math.max(a, b, c)
}

function gcd(d, e) {
    while (e) {
        [d, e] = [e, d % e]
    }
    return Math.abs(d)
}

console.log(convert(f))
console.log(isTriangle(a, b, c))
console.log(gcd(d, e))
