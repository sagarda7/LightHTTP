# LightHTTP  Android Library By Sagar Devkota
This library makes easy to perform HTTP request and handle responses easily
### Features
* Supports HTTP
* Memory Cache for JSON, Image
* Supports Various request methods
* Request Cancellation etc
* Auto evict item in cache if not used for recently

# Sample App
![Screen 1](https://bytebucket.org/sagarda7/mindvalley_sagardevkota_android_test/raw/ba8a261e94440353f3c16c740a1e87a14ae0218d/screenshots/screen.jpg?token=5718ae78097510fb5f76dfb0fe79f1171c196cd2 "")

# Download Sample App
[lighthttp_app.apk](http://www.sagardevkota.com.np/lighthttp_app.apk) and install it to your mobile

# Install

The library can be used as standard android project, which can be imported from build.gradle
or download [lighthttp.1.0.jar](https://bitbucket.org/sagarda7/mindvalley_sagardevkota_android_test/raw/5b094288f64ae1cafad3b2cef5579f03188b52ed/lighthttp1.0.jar) and put this in libs folder of your project


# Usage

### Make Standard Request 

```java
	...
	LightHTTP
			.from(mContext) //context
			.load(LightHTTP.Method.GET, "http://yoururl.com") //method, url
			.asJsonArray() // asJsonObject() , asBitmap() are supported, asXML() in future
			.setCallback(new HttpListener<JSONArray>() {
				@Override
				public void onRequest() {
					//fired when request begins
				}

				@Override
				public void onResponse(JSONArray data) {
					if(data!=null){
					   
					}
				}

				@Override
				public void onError() {
					//fired when error
				}

				@Override
				public void onCancel() {
					//fired when cancelled
				}
			});
```

### Pass Request Parameters 

```java
	...
	LightHTTP
			.from(mContext) //context
			.load(LightHTTP.Method.POST, "http://yoururl.com") 
			.setRequestParameter("foo","bar")
			.setRequestParameter("anotherkey","value")
			.asJsonObject() 
			.setCallback(new HttpListener<JSONObject>() {
				@Override
				public void onRequest() {
				
				}

				@Override
				public void onResponse(JSONObject data) {
					if(data!=null){
					   
					}
				}

				@Override
				public void onError() {
					//fired when error
				}

				@Override
				public void onCancel() {
					//fired when cancelled
				}
			});
```

### Pass Headers 

```java
	...
	LightHTTP
			.from(mContext) //context
			.load(LightHTTP.Method.POST, "http://yoururl.com") 
			.setHeaderParameter("Content-type","application/json") //we can add more
			.setRequestParameter("foo","bar")
			.asJsonObject() 
			.setCallback(new HttpListener<JSONObject>() {
				@Override
				public void onRequest() {
				
				}

				@Override
				public void onResponse(JSONObject data) {
					if(data!=null){
					   // do your parsing here
					}
				}

				@Override
				public void onError() {
					//fired when error
				}

				@Override
				public void onCancel() {
					//fired when cancelled
				}
			});
```

### Enable Cache

```java
	...
	public class SomeActivity extends AppCompatActivity {
	...
    CacheManager<JSONArray> cacheManager; // we can use JSONObject, Bitmap as generic type
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ...       
        cacheManager=new CacheManager<>(40*1024*1024); // 40mb
    }

    //event to make request
    public void btnLoadListClicked(View v){
        LightHTTP
                .from(this)
                .load(LightHTTP.Method.GET, "http://www.url.com")
                .asJsonArray()
                .setCacheManager(cacheManager)
                .setCallback(new HttpListener<JSONArray>() {
                    @Override
                    public void onRequest() {
						//fired when request begins
                    }

                    @Override
                    public void onResponse(JSONArray data) {
                        if(data!=null){
                            // do some stuff here
                        }
                    }

                    @Override
                    public void onError() {
						
                    }

                    @Override
                    public void onCancel() {

                    }
                });
			}

		}

```


### Load Image into ImageView with cache

```java
	...
	public class SomeActivity extends AppCompatActivity {
	...
    CacheManager<Bitmap> cacheManager; // we can use JSONObject, Bitmap as generic type
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ...       
        cacheManager=new CacheManager<>(40*1024*1024); // 40mb
		imgProfile= (ImageView) findViewById(R.id.image_profile);
    }

    //event to make request
    public void btnLoadImageClicked(View v){
        LightHTTP
                .from(this)
                .load(LightHTTP.Method.GET, "http://www.url.com/image.jpg")
                .asBitmap()
                .setCacheManager(cacheManager)
                .setCallback(new HttpListener<Bitmap>() {
                    @Override
                    public void onRequest() {
						//fired when request begins
                    }

                    @Override
                    public void onResponse(Bitmap data) {
                        if(data!=null){
                            // do some stuff here
							imgProfile.setImageBitmap(data);
                        }
                    }

                    @Override
                    public void onError() {
						
                    }

                    @Override
                    public void onCancel() {

                    }
                });
	}

}

```

### Cancel Request

```java
	...
	public class SomeActivity extends AppCompatActivity {
	...
    CacheManager<Bitmap> cacheManager; // we can use JSONObject, Bitmap as generic type
	Type<Bitmap> bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ...       
        cacheManager=new CacheManager<>(40*1024*1024); // 40mb
		imgProfile= (ImageView) findViewById(R.id.image_profile);
    }

    //event to make request
    public void btnLoadImageClicked(View v){
        bitmap= LightHTTP
                .from(this)
                .load(LightHTTP.Method.GET, "http://www.url.com/image.jpg")
                .asBitmap()
                .setCacheManager(cacheManager)
                .setCallback(new HttpListener<Bitmap>() {
                    @Override
                    public void onRequest() {
						//fired when request begins
                    }

                    @Override
                    public void onResponse(Bitmap data) {
                        if(data!=null){
                            // do some stuff here
							imgProfile.setImageBitmap(data);
                        }
                    }

                    @Override
                    public void onError() {
						
                    }

                    @Override
                    public void onCancel() {
						Log.d(TAG,"Request cancelled");
                    }
                });
	}
	
	public void btnCancelRequestClicked(View v){
		if(bitmap!=null)
			bitmap.cancel();
	}

}

```

### More Info about library
* All calls are made through thread safe singleton instance of LightHTTP, used singleton pattern here
* Most of the functions and classes in library are well commented
* I have tried to make a class be responsible to single purpose (Single Responsibility), eg. See any model class
* I have used Builder design pattern with method chaining for making it easy to use the library calls
* All  classes in datatypes package extend abstract class called Type, so there is no need to modify class, we can extend also create new type class or extend existing to meet Open/Close principle
* As all type classes(JsonArrayType, JsonObjectType, BitmapType) extend abstract class Type, so these can replace any Type , so derived classes can substitute parent class Type.
* Classes are not forced to implement interface that are not required, so I tried to avoid it to meet I in SOLID
* CacheManager class implements CacheManagerInterface and setCacheManager method in Type class depends on abstraction i.e interface, so it is loosely coupled, tried this for D in SOLID
* When we need to add support for XML, just add new class called XMLDataType and make XMLRequestTask Class without touching existing class in datatypes package and request class
* I have created few(Not All) unit tests of library in MindValleyLibraryTest class
* The coding may have some improvements and missing as this is 2 days work.  and contribution are welcomed :) 

### Contact
Sagar Devkota
sagarda7@yahoo.com
+9779856032592
Skype: sagarda7