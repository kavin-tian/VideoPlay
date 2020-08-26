# VideoPlay

(一). android的原生的视频播放方式有三种，分别为:
  1.利用Intent调用系统自带的播放器或者安装的第三方播放器
    Uri uri=Uri.parse("http://123.150.52.227/0725695b00000000-1415769042-1960016430/data5/vkplx.video.qq.com/flv/74/164/a0015193bxf.p203.1.mp4");
    Intent intent=new Intent(Intent.ACTION_VIEW);
    intent.setDataAndType(uri,"video/*");
    startActivity(intent);


  2.利用VideoView来播放视频。
  3.利用MediaPlayer类和SufferView来播放视频
  
  高版本系统报错: MediaPlayerNative: error (1, -2147483648) 
  解决方案一:允许明文通信 android:usesCleartextTraffic="true"
  解决方案二：让服务端将接口的http全部改成https就好了

总结: 高低版本系统只用3gp格式兼容好一点, MP4低版本不太兼容, 可能MP4格式有很多种造成的!不是支持所有mp4格式

android：usesCleartextTraffic 指示应用程序是否打算使用明文网络流量，例如明文HTTP。
目标API级别为27或更低的应用程序的默认值为“ true”。面向API级别28或更高级别的应用默认为“ false”。
当属性设置为“ false”时，平台组件（例如，HTTP和FTP堆栈，DownloadManager和MediaPlayer）将拒绝应用程序使用明文流量的请求。
强烈建议第三方库也采用此设置。避免明文通信的主要原因是缺乏机密性，真实性和防篡改保护；网络攻击者可以窃听所传输的数据，并且还可以对其进行修改而不会被检测到。


(二).第三方视频播放框架
GitHub上最著名的Android播放器开源项目大全

4.GSYVideoPlayer
项目地址： https://github.com/CarGuo/GSYVideoPlayer

介绍：视频播放器，支持基本的拖动，声音、亮度调节，支持边播边缓存，支持视频本身自带rotation的旋转（90,270之类），重力旋转与手动旋转的同步支持，支持列表播放 
，直接添加控件为封面，列表全屏动画
