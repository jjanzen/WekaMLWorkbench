import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;

public class StartWeka {
	//https://www.youtube.com/watch?v=q3Gf6kqaJWA
	// convert csv to .arff http://weka.sourceforge.net/doc.stable/weka/core/converters/package-summary.html
	public static void main(String[] args) throws Exception {
		
		BufferedReader breader = null;
	//	breader = new BufferedReader(new FileReader("data/weather.arff"));
		breader = new BufferedReader(new FileReader("data/moversTrainingWeka2.arff"));
		
		Instances train = new Instances(breader);
		
		//train.numAttitibutes = # of attributes in csv
		// -1 will identify the class (true of false of Class to predict)
		train.setClassIndex(train.numAttributes() -1);
		
		breader.close();
		
		NaiveBayes nB = new NaiveBayes();
		nB.buildClassifier(train);
		Evaluation eval = new Evaluation(train);
		eval.crossValidateModel(nB, train, 22, new Random(1));  // 22 = 22 fold cross validation
		System.out.println(eval.toSummaryString("\nResutls\n======\n", true));
		System.out.println(eval.fMeasure(1) + " "+eval.precision(1) + " " + eval.recall(1));
		
		//System.out.println(eval.)
	}

}
