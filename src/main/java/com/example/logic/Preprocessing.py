import re
from nltk.tokenize import word_tokenize
from nltk.corpus import stopwords
from sklearn.feature_extraction.text import CountVectorizer
from nltk.stem import WordNetLemmatizer
from sklearn.model_selection import train_test_split
from keras.layers import RNN
import tensorflow as tf
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, SimpleRNN
import numpy as np
import pandas as pd
import nltk
from sklearn.feature_extraction.text import CountVectorizer
nltk.data.path.append('.')

in_domain_data = [
    "Which lectures are there on Monday at 9",
    "How is the weather in Maastricht",
    "What's the weather like in Paris"
]

out_of_domain_data = [
    "Can you recommend a good restaurant?",
    "How tall is the Eiffel Tower?",
    "What time does the movie start?"
]

def preprocess_data_first_step(data):
    preprocessed_data = []
    stop_words = set(stopwords.words('english'))
    lemmatizer = WordNetLemmatizer()
    
    for sentence in data:
        tokens = word_tokenize(sentence)
        tokens = [lemmatizer.lemmatize(token.lower()) for token in tokens if token.isalpha()]
        tokens = [token for token in tokens if token not in stop_words]
        preprocessed_data.append(' '.join(tokens))
    
    vectorizer = CountVectorizer()
    X = vectorizer.fit_transform(preprocessed_data)
    
    return X.toarray()

preprocessed_in_domain_data = preprocess_data_first_step(in_domain_data)
print("Preprocessed In-Domain Data:")
print(preprocessed_in_domain_data)

preprocessed_out_of_domain_data = preprocess_data_first_step(out_of_domain_data)
print("\nPreprocessed Out-of-Domain Data:")
print(preprocessed_out_of_domain_data)
all_data = in_domain_data + out_of_domain_data


labels = [1]*len(in_domain_data) + [0]*len(out_of_domain_data)


preprocessed_data = preprocess_data_first_step(all_data)


X_train, X_test, y_train, y_test = train_test_split(preprocessed_data, labels, test_size=0.3, random_state=42)

X_train_rnn = X_train.reshape((X_train.shape[0], 1, X_train.shape[1]))
X_test_rnn = X_test.reshape((X_test.shape[0], 1, X_test.shape[1]))
input_dim = X_train.shape[1]
rnn_units = 10

model = Sequential()
model.add(SimpleRNN(units=rnn_units, input_shape=(None, input_dim), activation='relu'))
model.add(Dense(1, activation='sigmoid'))

model.compile(optimizer='adam', loss='binary_crossentropy', metrics=['accuracy'])

X_train_rnn = X_train.reshape((X_train.shape[0], 1, X_train.shape[1]))
X_test_rnn = X_test.reshape((X_test.shape[0], 1, X_test.shape[1]))

y_train = np.array(y_train)
y_test = np.array(y_test)

model.fit(X_train_rnn, y_train, epochs=13, verbose=1)

# Test the model
test_loss, test_accuracy = model.evaluate(X_test_rnn, y_test)
print(f'Test Loss: {test_loss}, Test Accuracy: {test_accuracy}')