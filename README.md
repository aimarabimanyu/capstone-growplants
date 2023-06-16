# Capstone-GrowPlants-Machine Learning Part
Predict plant diseases (rice, corn, and potato) through leaf images with 13 classes.

# Documentation
1. Datasets : We use approximately 9100 Datasets in total <br />
   Source: <br />
   https://www.kaggle.com/datasets/vipoooool/new-plant-diseases-dataset (corn and potato datasets) <br />
   https://www.kaggle.com/datasets/muhammadardiputra/potato-leaf-disease-dataset (potato datasets) <br />
   https://archive.ics.uci.edu/ml/datasets/rice+leaf+diseases (rice datasets) <br />
   https://data.mendeley.com/datasets/fwcj7stb8r (rice datasets) <br />
2. Download the dataset and upload it to google drive
3. Import dataset from drive to google colab
4. Preprocessing and augmentation of data
5. Modeling using CNN and fine tuning of EfficientNetB3 with 150 fine tuning layers
6. Compile and train the model
7. Test model using test dataset
8. Evaluate model using accuracy value, loss function, confusion matrix, and classification report
9. Save model to h5 format and then convert it using tensorflow js

# Class Prediction
There are 13 classes to be predicted by the model:
1. Padi sehat
2. Padi hispa
3. Padi hawar daun
4. Padi blast
5. Padi bercak cokelat
6. Kentang virus
7. Kentang sehat
8. Kentang busuk daun
9. Kentang bercak kering
10. Jagung sehat
11. Jagung karat daun
12. Jagung hawar daun
13. Jagung bercak daun
