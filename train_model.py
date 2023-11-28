import h2o
from h2o.estimators.gbm import H2OGradientBoostingEstimator
# Initialize the H2O environment
h2o.init()
# Load your dataset
# Replace “prostate.csv” with the path to your dataset
h2o_df = h2o.load_dataset("german_credit_data.csv")
df=h2o_df.as_data_frame()

#get all the columns except the target column
columns = list(df.columns)
columns.remove('Risk')

h2o_df["Risk"] = h2o_df["Risk"].asfactor()

# Define and train the model
model = H2OGradientBoostingEstimator(distribution="bernoulli",
                                     ntrees=100,
                                     max_depth=4,
                                     learn_rate=0.1)
model.train(y="Risk",
            x=columns,
            training_frame=h2o_df)
# Download the MOJO and the h2o-genmodel.jar file
modelfile = model.download_mojo(path="~/experiment/", get_genmodel_jar=True)
print("Model saved to " + modelfile)

