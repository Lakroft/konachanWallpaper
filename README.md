# konachanWallpaper
My small fun project. Taking images from konachan.com and setting as a wallpaper.

Properties of application in app.properties:
* tags - tags of images for searching. Delimiter "+".
* blacklist - undesirable tags. Images with any of them would de ignored. Delimiter ",".
* delay - delay before setting .
* size_filter - filtering images, smaller then min_image_height or min_image_width.
* min_image_height - would be used, if size_filter=true
* min_image_width - would be used, if size_filter=true
* useproxy - use proxy connection if konachan is blocked for you.
* proxy.0 - proxy address in form <ip:port>. You can use several poxies, numirate them like proxy.1, proxy.2...
* keep_img - images would be saved on disk.
* img_catalog - catalog for saved images, if keep_img=true.
* show_stack - show stack of exceptions.

TODOs:
* DONE:Keep downloaded images.
* DONE:Image size filter(don't use images, whith smaller height or width then in property)
* DONE:List of proxies. If one is down, app will try next. First workin proxy moves to first pos. in list. 
* DONE:Read proxies list from .properties file
* DONE:Generate exception if all proxies down.
* DONE:Proxies .properties file. Reads on start.
* DONE:Tags and blacklist .properties file.
* DONE:"Image file catalog" and "Keep downloaded images" properties in .properties file.
* DONE:"Scheduling time" property in .properties file.
* Linux mode for gnome and cinnamon.
* Startup script and icon. DONE:script
