import breeze.linalg.{DenseMatrix, DenseVector, csvread, sum}
import breeze.numerics.pow
import breeze.stats.mean
import breeze.stats.regression.leastSquares

import java.io.{File, PrintWriter}
import java.util.logging.{FileHandler, Logger, SimpleFormatter}


object Main extends App {

  System.setProperty(
    "java.util.logging.SimpleFormatter.format",
    "%1$tF %1$tT %4$s %5$s%6$s%n"
  )

  if (args.length != 3) {
    println("WARNING! Required next 3 arguments:\nTrain file path;\nTest file path;\nOutput file path")
  }

  val trainFileName = args(0)
  val testFileName = args(1)
  val outputFileName = args(2)

  val logger = Logger.getLogger("Linear Regression implementation")
  val handler = new FileHandler("main.txt")

  val formatter = new SimpleFormatter()
  handler.setFormatter(formatter)
  logger.addHandler(handler)

  logger.info(f"\nr_train_file: ${trainFileName}\nr_test_file: ${testFileName}\nw_output_file: ${outputFileName}\n")

  val train_file: File = new File(trainFileName)
  val test_file: File = new File(testFileName)

  logger.info(f"\nFiles found: OK...")

  val train_data: DenseMatrix[Double] = csvread(train_file, skipLines=1)
  val test_data: DenseMatrix[Double] = csvread(test_file, skipLines=1)

  logger.info(f"\nReading is OK...")

  val train_m = train_data.toDenseMatrix
  val test_m = test_data.toDenseMatrix

  val y_train: DenseVector[Double] = train_m(::, -1)
  val y_test: DenseVector[Double] = test_m(::, -1)

  val ones_train: DenseVector[Double] = DenseVector.ones(train_data.rows)
  val ones_test: DenseVector[Double] = DenseVector.ones(test_data.rows)

  val X_train = DenseMatrix.horzcat(new DenseMatrix(train_data.rows,1, ones_train.toArray), train_m(::, 0 until train_data.cols - 1))
  val X_test = DenseMatrix.horzcat(new DenseMatrix(test_data.rows,1, ones_test.toArray), test_m(::, 0 until test_data.cols - 1))

  logger.info(f"\nData preparation is OK...")

  val fit = leastSquares(X_train, y_train)

  logger.info(f"\nModel is fitted: OK...")

  val predict_train = X_train * fit.coefficients
  val predict_test = X_test * fit.coefficients

  logger.info(f"\nPredictions succeeded: OK...")

  val writer = new PrintWriter(new File(outputFileName))

  for (row <- predict_test) {
    writer.write(f"$row\n")
  }
  writer.close()

  logger.info(f"\nTest pred was written: OK...")

  val r2_train = 1 - (sum(pow(y_train - predict_train, 2)) / sum(pow(y_train - mean(predict_train), 2)))
  val r2_test = 1 - (sum(pow(y_test - predict_test, 2)) / sum(pow(y_test - mean(predict_test), 2)))

  logger.info(f"\nr**2 on train: ${r2_train}%.4f\nr**2 on test: ${r2_test}%.4f\n")
  logger.info(f"\nEstimation finished: OK...")
  logger.info(f"\nEnd of program.")
}
