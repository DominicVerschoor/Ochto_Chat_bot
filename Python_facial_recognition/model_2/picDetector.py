import cv2 as cv;

img= cv.imread("img.jpg")
#cv.imshow('person',img)

gray=cv.cvtColor(img,cv.COLOR_BGR2GRAY)
#cv.imshow('gray person',gray)

haar_cascade=cv.CascadeClassifier("Python_facial_recognition/model_2/venv/haar_face.xml")
faces_rect=haar_cascade.detectMultiScale(gray,scaleFactor=1.1,minNeighbors=10)

for(x,y,w,h) in faces_rect:
    cv.rectangle(img,(x,y),(x+w,y+h),(0,255,0),thickness=2)

cv.imshow('detected faces',img)

print(f'Number of faces found = {len(faces_rect)}')

cv.waitKey(0)
