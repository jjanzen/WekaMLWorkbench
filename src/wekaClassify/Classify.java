package wekaClassify;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.ADTree;
import weka.classifiers.trees.BFTree;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.FT;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.NBTree;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

public class Classify {

	public static void main(String[] args) throws Exception {
		BufferedReader breader = null;
		breader = new BufferedReader(new FileReader("data/moversTrainingWeka2.arff"));

		Instances train = new Instances(breader);
	
		// train.numAttitibutes = # of attributes in csv
		// -1 will identify the class (true of false of Class to predict)
		train.setClassIndex(train.numAttributes() - 1);

		// true count = 11,328, false count = 10,000
		breader = new BufferedReader(new FileReader("data/moversTestWeka2.arff"));
		Instances test = new Instances(breader);
		test.setClassIndex(test.numAttributes() - 1);

		breader.close();

		J48 tree = new J48(); // new instance of tree
		tree.setUnpruned(true);
		// NBTree tree = new NBTree();
		// PART tree = new PART();  .37
		//DecisionTable tree = new DecisionTable();
		// DecisionStump tree = new DecisionStump();
	//	RandomForest tree = new RandomForest();
		//ADTree tree = new ADTree();
		//BFTree tree = new BFTree();
	//	FT tree = new FT();
		//NaiveBayes tree = new NaiveBayes();
		 //tree.setMaxDepth(100);
		// tree.setOptions("-U") // set the options
		tree.buildClassifier(train); // build classifier

		Instances labeledTrain = new Instances(train);
		Evaluation eval = new Evaluation(train);
		eval.crossValidateModel(tree, train, 22, new Random(1));  // 22 = 22 fold cross validation

		Instances labeled = new Instances(test);
		
		// added to pull accuracy
		int countTrue = 0;
		int countFalse = 0;
		int countTrueTrain = 0;
		int countFalseTrain = 0;
		double accuracyTrue;
		double accuracyFalse;
		// label instances
		for (int i = 0; i < test.numInstances(); i++) {
			double clsLabel = tree.classifyInstance(test.instance(i));
			labeled.instance(i).setClassValue(clsLabel);

			String trueValue = labeled.instance(i).toString(21);
			String trueValueTrain = labeledTrain.instance(i).toString(21);  // System.out.println(trueValue);  // System.out.println(trueValueTrain);
			if (trueValue.equals("t")) {
				countTrue++;
			} else {
				countFalse++;
			}

			if (trueValueTrain.equals(trueValue)) {
				countTrueTrain++;  //	System.out.println("matched row "+ String.valueOf(i));
			}
		}

		accuracyTrue = (double)countTrueTrain / (double)21328;
		System.out.println("matched: " + String.valueOf(countTrueTrain)
				+ " of 21,328. An accuracy true of "
				+ String.valueOf(accuracyTrue));

		// save label data
		System.out.println("Total True: " + String.valueOf(countTrue));
		System.out.println("Total False: " + String.valueOf(countFalse));
		System.out.println("Accuracy True : " + String.valueOf(accuracyTrue));
		// System.out.println(labeled.toString());
		BufferedWriter writer = new BufferedWriter(new FileWriter(
				"data/outputLabeled.arff")); // does not work
		writer.write(labeled.toString());
		writer.close();
	}

}
