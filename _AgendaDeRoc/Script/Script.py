import requests
from bs4 import BeautifulSoup
import mysql.connector
from selenium import webdriver

# Ruta del controlador del navegador (ejemplo para Chrome)
driver_path = '.\Script'

# Crear una instancia del controlador del navegador
driver = webdriver.Chrome(executable_path=driver_path)

# URL de la página web
url = 'https://tickets.movistararena.com.ar/'

# Obtener el contenido de la página web después de cargarla completamente
driver.get(url)
html = driver.page_source

# Crear objeto BeautifulSoup para analizar el HTML
soup = BeautifulSoup(html, 'html.parser')

# Cerrar el controlador del navegador
driver.quit()

# Conectarse a la base de datos MySQL
cnx = mysql.connector.connect(user='root', password='root',
                              host='localhost', database='recitales')
cursor = cnx.cursor()

# Crear tabla en la base de datos si no existe
cursor.execute('''CREATE TABLE IF NOT EXISTS eventos (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    titulo VARCHAR(255),
                    fecha VARCHAR(255),
                    lugar VARCHAR(255),
                    imagen VARCHAR(255)
                )''')

# Obtener los elementos de la página web que deseas extraer
eventos = soup.find_all('div', class_='event-card')

# Insertar la información en la base de datos
for evento in eventos:
    titulo = evento.find('h4').text.strip()
    fecha = evento.find('div', class_='fecha').text.strip()
    lugar = evento.find('div', class_='lugar').text.strip()
    imagen = evento.find('img')['src'].strip()
    
    insert_query = "INSERT INTO eventos (titulo, fecha, lugar, imagen) VALUES (%s, %s, %s, %s)"
    data = (titulo, fecha, lugar, imagen)
    cursor.execute(insert_query, data)
# Guardar los cambios y cerrar la conexión a la base de datos
cnx.commit()
cnx.close()

print("La información ha sido guardada en la base de datos.")