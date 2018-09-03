package com.filehandler;


import com.exception.ValidationException;
import org.junit.Assert;
import org.junit.Test;


public class DownloaderTest {
    @Test
    public void testCallMethod_SuccessFully() throws Exception {
        Downloader downloader=new Downloader("http://www.gstatic.com/webp/gallery/1.jpg","testDownloads");
        String result=downloader.call();
        Assert.assertNotNull(result);
    }

    @Test(expected = ValidationException.class)
    public void testCallMethod_WhenSourceisNull_ThrowsException() throws Exception {
        Downloader downloader=new Downloader(null,"testDownloads");
        downloader.call();
    }

    @Test(expected = ValidationException.class)
    public void testCallMethod_WhenLocationisNull_ThrowsException() throws Exception {
        Downloader downloader=new Downloader("http://www.gstatic.com/webp/gallery/1.jpg",null);
        downloader.call();
    }
}
