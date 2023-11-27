import java.io.*;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.prediction.*;
import hex.genmodel.MojoModel;

public class Main {
    public static void main(String[] args) throws Exception {
        args=new String[]{"68","2","2","0","6"};
        String result = getPrediction(args);
        System.out.println(result);
    }

    private static String getPrediction(String[] args) throws Exception {
        EasyPredictModelWrapper model = new EasyPredictModelWrapper(MojoModel.load("GBM_model_python_1701049837667_1.zip"));

        RowData row = new RowData();
        row.put("AGE", args[0]);
        row.put("RACE", args[1]);
        row.put("DCAPS", args[2]);
        row.put("VOL", args[3]);
        row.put("GLEASON", args[4]);

        BinomialModelPrediction p = model.predictBinomial(row);

        StringBuilder sb = new StringBuilder();
        sb.append("Has penetrated the prostatic capsule (1=yes; 0=no): ").append(p.label).append("\n");
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
