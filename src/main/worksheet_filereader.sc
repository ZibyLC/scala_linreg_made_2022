println("Pregnancies, Glucose, BloodPressure, SkinThickness, Insulin, BMI, DiabetesPedigreeFunction, Age, Outcome")
  val bufferedSource = io.Source.fromFile("D:\\BigData_made\\HW3\\scala_linreg_made_2022\\src\\main\\diabetes.csv")
  for (line <- bufferedSource.getLines) {
    val cols = line.split(",").map(_.trim)
    // do whatever you want with the columns here
    println(s"${cols(0)}|${cols(1)}|${cols(2)}|${cols(3)}")
  }
  bufferedSource.close