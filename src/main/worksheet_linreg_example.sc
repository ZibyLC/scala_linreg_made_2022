import breeze.linalg.{DenseMatrix, DenseVector}
import breeze.stats.regression.leastSquares

val X = DenseMatrix((1.0, 0.0), (1.1, 1.0), (1.1, 2.0), (1.5, 3.0), (1.6, 3.2))
val y = DenseVector(1.0, 2.2, 3.0, 4.3, 5.0)
val res = leastSquares(X, y)

println(res.coefficients.data(0))
println(res.coefficients.data(1))

println(res.rSquared)