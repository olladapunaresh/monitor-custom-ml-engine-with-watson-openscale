import java.io.*;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.prediction.*;
import hex.genmodel.MojoModel;

public class main {
    public static void main(String[] args) throws Exception {
        args=new String[]{"0_to_200","31","credits_paid_to_date","other","1889","100_to_500","less_1","3"	,"female","none","3","savings_insurance","32","none","own","1","skilled","1","none","yes"};
        String result = getPrediction(args);
        System.out.println(result);
    }

    private static String getPrediction(String[] args) throws Exception {
        EasyPredictModelWrapper model = new EasyPredictModelWrapper(MojoModel.load("GBM_model_python_1701150866786_1.zip"));

        RowData row = new RowData();
        row.put("CheckingStatus", args[0]);
        row.put("LoanDuration", args[1]);
        row.put("CreditHistory", args[2]);
        row.put("LoanPurpose", args[3]);
        row.put("LoanAmount", args[4]);
        row.put("ExistingSavings", args[5]);
        row.put("EmploymentDuration", args[6]);
        row.put("InstallmentPercent", args[7]);
        row.put("Sex", args[8]);
        row.put("OthersOnLoan", args[9]);
        row.put("CurrentResidenceDuration", args[10]);
        row.put("OwnsProperty", args[11]);
        row.put("Age", args[12]);
        row.put("InstallmentPlans", args[13]);
        row.put("Housing", args[14]);
        row.put("ExistingCreditsCount", args[15]);
        row.put("Job", args[16]);
        row.put("Dependents", args[17]);
        row.put("Telephone", args[18]);
        row.put("ForeignWorker", args[19]);

        BinomialModelPrediction p = model.predictBinomial(row);

        StringBuilder sb = new StringBuilder();
        sb.append("Predicted Credit Risk  (1= No Risk; 0=Risk): ").append(p.label).append("\n");
        sb.append("Class probabilities: ");
        for (int i = 0; i < p.classProbabilities.length; i++) {
            if (i > 0) {
                sb.append(",");
            }
            sb.append(p.classProbabilities[i]);
        }
        return sb.toString();
    }
}
