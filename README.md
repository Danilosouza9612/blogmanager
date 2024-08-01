![Alt text](relative%20path/to/img.jpg?raw=true "Blog architecture")

An API Spring Boot blog managment application. You could create blogs with pages, and you could make posts with uploaded images for it. Posts are categorized and taggable. 
Images are storaged in Amazon S3. In local development, the application uses LocalStack to simulate Amazon S3 storage.

The project is executed using this docker compose command:

```
docker compose up
```

The database with tables are created automatically

After you ran the application, you will need to create the Amazon S3 bucket using command bellow if you want to upload images to use them in some posts.

```
docker compose exec local_stack awslocal s3api create-bucket --bucket blog-files
```

Some features are being developed and I'm developing the API documentation.

