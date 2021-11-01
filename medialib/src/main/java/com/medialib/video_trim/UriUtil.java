package com.medialib.video_trim;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import androidx.annotation.Nullable;

import java.io.File;
import java.util.UUID;


/**
 *  URI Helper工具类
 * @author lincun
 * @date 2019/6/30 20:04
 **/
public class UriUtil {

    /**
     * http scheme for URIs
     */
    public static final String HTTP_SCHEME = "http";
    public static final String HTTPS_SCHEME = "https";

    /**
     * File scheme for URIs
     */
    public static final String LOCAL_FILE_SCHEME = "file";

    /**
     * Content URI scheme for URIs
     */
    public static final String LOCAL_CONTENT_SCHEME = "content";

    /**
     * URI prefix (including scheme) for contact photos
     */
    private static final String LOCAL_CONTACT_IMAGE_PREFIX =
            Uri.withAppendedPath(ContactsContract.AUTHORITY_URI, "display_photo").getPath();

    /**
     * Asset scheme for URIs
     */
    public static final String LOCAL_ASSET_SCHEME = "asset";

    /**
     * Resource scheme for URIs
     */
    public static final String LOCAL_RESOURCE_SCHEME = "res";

    /**
     * Resource scheme for fully qualified resources which might have a package name that is different
     * than the application one. This has the constant value of "android.resource".
     */
    public static final String QUALIFIED_RESOURCE_SCHEME = ContentResolver.SCHEME_ANDROID_RESOURCE;

    /**
     * Data scheme for URIs
     */
    public static final String DATA_SCHEME = "data";

    /**
     * Check if uri represents network resource
     *
     * @param uri uri to check
     * @return true if uri's scheme is equal to "http" or "https"
     */
    public static boolean isNetworkUri(@Nullable Uri uri) {
        final String scheme = getSchemeOrNull(uri);
        return HTTPS_SCHEME.equals(scheme) || HTTP_SCHEME.equals(scheme);
    }

    /**
     * Check if uri represents local file
     *
     * @param uri uri to check
     * @return true if uri's scheme is equal to "file"
     */
    public static boolean isLocalFileUri(@Nullable Uri uri) {
        final String scheme = getSchemeOrNull(uri);
        return LOCAL_FILE_SCHEME.equals(scheme);
    }

    /**
     * Check if uri represents local content
     *
     * @param uri uri to check
     * @return true if uri's scheme is equal to "content"
     */
    public static boolean isLocalContentUri(@Nullable Uri uri) {
        final String scheme = getSchemeOrNull(uri);
        return LOCAL_CONTENT_SCHEME.equals(scheme);
    }

    /**
     * Checks if the given URI is a general Contact URI, and not a specific display photo.
     *
     * @param uri the URI to check
     * @return true if the uri is a Contact URI, and is not already specifying a display photo.
     */
    public static boolean isLocalContactUri(Uri uri) {
        return isLocalContentUri(uri)
                && ContactsContract.AUTHORITY.equals(uri.getAuthority())
                && !uri.getPath().startsWith(LOCAL_CONTACT_IMAGE_PREFIX);
    }

    /**
     * Checks if the given URI is for a photo from the device's local media store.
     *
     * @param uri the URI to check
     * @return true if the URI points to a media store photo
     */
    public static boolean isLocalCameraUri(Uri uri) {
        String uriString = uri.toString();
        return uriString.startsWith(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString())
                || uriString.startsWith(MediaStore.Images.Media.INTERNAL_CONTENT_URI.toString());
    }

    /**
     * Check if uri represents local asset
     *
     * @param uri uri to check
     * @return true if uri's scheme is equal to "asset"
     */
    public static boolean isLocalAssetUri(@Nullable Uri uri) {
        final String scheme = getSchemeOrNull(uri);
        return LOCAL_ASSET_SCHEME.equals(scheme);
    }

    /**
     * Check if uri represents local resource
     *
     * @param uri uri to check
     * @return true if uri's scheme is equal to {@link #LOCAL_RESOURCE_SCHEME}
     */
    public static boolean isLocalResourceUri(@Nullable Uri uri) {
        final String scheme = getSchemeOrNull(uri);
        return LOCAL_RESOURCE_SCHEME.equals(scheme);
    }

    /**
     * Check if uri represents fully qualified resource URI.
     *
     * @param uri uri to check
     * @return true if uri's scheme is equal to {@link #QUALIFIED_RESOURCE_SCHEME}
     */
    public static boolean isQualifiedResourceUri(@Nullable Uri uri) {
        final String scheme = getSchemeOrNull(uri);
        return QUALIFIED_RESOURCE_SCHEME.equals(scheme);
    }

    /**
     * Check if the uri is a data uri
     */
    public static boolean isDataUri(@Nullable Uri uri) {
        return DATA_SCHEME.equals(getSchemeOrNull(uri));
    }

    /**
     * @param uri uri to extract scheme from, possibly null
     * @return null if uri is null, result of uri.getScheme() otherwise
     */
    @Nullable
    public static String getSchemeOrNull(@Nullable Uri uri) {
        return uri == null ? null : uri.getScheme();
    }

    /**
     * A wrapper around {@link Uri#parse} that returns null if the input is null.
     *
     * @param uriAsString the uri as a string
     * @return the parsed Uri or null if the input was null
     */
    public static Uri parseUriOrNull(@Nullable String uriAsString) {
        return uriAsString != null ? Uri.parse(uriAsString) : null;
    }

    /**
     * Get the path of a file from the Uri.
     *
     * @param contentResolver the content resolver which will query for the source file
     * @param srcUri The source uri
     * @return The Path for the file or null if doesn't exists
     */
    @Nullable
    public static String getRealPathFromUri(ContentResolver contentResolver, final Uri srcUri) {
        String result = null;
        if (isLocalContentUri(srcUri)) {
            Cursor cursor = null;
            try {
                cursor = contentResolver.query(srcUri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (idx != -1) {
                        result = cursor.getString(idx);
                    }
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        } else if (isLocalFileUri(srcUri)) {
            result = srcUri.getPath();
        }
        return result;
    }

    /**
     * Returns a URI for a given file using {@link Uri#fromFile(File)}.
     *
     * @param file a file with a valid path
     * @return the URI
     */
    public static Uri getUriForFile(File file) {
        return Uri.fromFile(file);
    }

    /**
     * Return a URI for the given resource ID.
     * The returned URI consists of a {@link #LOCAL_RESOURCE_SCHEME} scheme and
     * the resource ID as path.
     *
     * @param resourceId the resource ID to use
     * @return the URI
     */
    public static Uri getUriForResourceId(int resourceId) {
        return new Uri.Builder()
                .scheme(LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(resourceId))
                .build();
    }

    /**
     * Returns a URI for the given resource ID in the given package. Use this method only if you need
     * to specify a package name different to your application's main package.
     *
     * @param packageName a package name (e.g. com.facebook.myapp.plugin)
     * @param resourceId to resource ID to use
     * @return the URI
     */
    public static Uri getUriForQualifiedResource(String packageName, int resourceId) {
        return new Uri.Builder()
                .scheme(QUALIFIED_RESOURCE_SCHEME)
                .authority(packageName)
                .path(String.valueOf(resourceId))
                .build();
    }

    public static String getNameFromUrl(String url) {
        if (url != null) {
            if (url.equalsIgnoreCase("")) {
                return UUID.randomUUID().toString();
            } else {
                return UUID.nameUUIDFromBytes(url.getBytes()).toString();
            }
        } else {
            return UUID.randomUUID().toString();
        }
    }

    public static String getUrlByUri(Context context, Uri uri) {
        String s = uri.getScheme();
        String url = null;
        if ("file".equals(s))
            url = uri.getPath();
        else if ("content".equals(s)) {
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver().query(uri,
                        new String[] { "_data" }, null, null, null);
                int data = cursor.getColumnIndexOrThrow("_data");
                cursor.moveToFirst();
                url = cursor.getString(data);
                //cursor.close();
            }catch(Exception e) {
                e.printStackTrace();
            }finally {
                if(cursor != null) {
                    cursor.close();
                }
            }
        } else {
            url = uri.toString();
        }
        return url;
    }
}
