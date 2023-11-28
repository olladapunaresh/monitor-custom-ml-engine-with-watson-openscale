import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import hex.genmodel.easy.RowData;
import hex.genmodel.easy.EasyPredictModelWrapper;
import hex.genmodel.easy.prediction.*;
import hex.genmodel.MojoModel;

@SpringBootApplication
public class PredictionApplication {

    public static void main(String[] args) {
        SpringApplication.run(PredictionApplication.class, args);
    }

    @RestController
    public class PredictionController {

        @PostMapping("/predict")
        public String getPrediction(@RequestBody PredictionRequest request) throws Exception {
            return predict(request);
        }

        private String predict(PredictionRequest request) throws Exception {
            EasyPredictModelWrapper model = new EasyPredictModelWrapper(MojoModel.load("GBM_model_python_1701049837667_1.zip"));
            RowData row = new RowData();
            row.put("AGE", request.age);
            row.put("RACE", request.race);
            row.put("DCAPS", request.dcaps);
            row.put("VOL", request.vol);
            row.put("GLEASON", request.gleason);

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

    public static class PredictionRequest {
        public String age;
        public String race;
        public String dcaps;
        public String vol;
        public String gleason;
    }
}

