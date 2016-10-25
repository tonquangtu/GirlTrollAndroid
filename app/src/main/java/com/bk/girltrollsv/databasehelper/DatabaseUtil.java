package com.bk.girltrollsv.databasehelper;

import android.content.ContentValues;
import android.database.Cursor;

import com.bk.girltrollsv.constant.AppConstant;
import com.bk.girltrollsv.model.BaseInfoMember;
import com.bk.girltrollsv.model.Feed;
import com.bk.girltrollsv.model.ImageInfo;
import com.bk.girltrollsv.model.Member;
import com.bk.girltrollsv.model.Video;
import com.bk.girltrollsv.util.AccountUtil;

import java.util.ArrayList;

/**
 * Created by Dell on 17-Sep-16.
 */
public class DatabaseUtil {

    public static ArrayList<Feed> getFeedsOffset(int limit, int rowOffset) {

        return loadFeeds(SQLStatement.getQueryFeedOffset(limit, rowOffset), null);
    }

    public static ArrayList<Feed> getFeedsPaging(int limit, int startFeedId) {

        return loadFeeds(SQLStatement.getQueryFeedPaging(limit, startFeedId), null);
    }

    private static ArrayList<Feed> loadFeeds(String query, String [] args) {

        Cursor cursor = DatabaseOpenHelper.getDbReader().rawQuery(query, args);
        ArrayList<Feed> feeds = new ArrayList<>();
        Feed feed;
        String accountId = AccountUtil.getAccountId();
        if (cursor != null) {
            if (cursor.moveToFirst()) {

                while (!cursor.isAfterLast()) {

                    int feedId = cursor.getInt(SQLStatement.FeedTable.FEED_ID_INDEX);
                    String title = cursor.getString(SQLStatement.FeedTable.TITLE_INDEX);
                    String time = cursor.getString(SQLStatement.FeedTable.TIME_INDEX);
                    int like = cursor.getInt(SQLStatement.FeedTable.LIKE_INDEX);
                    int comment = cursor.getInt(SQLStatement.FeedTable.COMMENT_INDEX);
                    String school = cursor.getString(SQLStatement.FeedTable.SCHOOL_INDEX);
                    String memberId = cursor.getString(SQLStatement.FeedTable.MEMBER_ID_INDEX);
                    String username = cursor.getString(7);
                    String avatarUrl = cursor.getString(8);
                    int isLike = getIsLike(feedId, accountId);
                    BaseInfoMember member = new BaseInfoMember(memberId, username, avatarUrl);
                    Video video = getVideoOfFeed(feedId);
                    ArrayList<ImageInfo> imageInfos = getImageOfFeed(feedId);

                    feed = new Feed(feedId, title, time, isLike, like, comment, school, member, video, imageInfos);
                    feeds.add(feed);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }
        return feeds;
    }


    public static ArrayList<ImageInfo> getImageOfFeed(int feedId) {

        ArrayList<ImageInfo> imageInfos = null;
        Cursor cursor = DatabaseOpenHelper.getDbReader().rawQuery(SQLStatement.QUERY_IMAGE_INFO, new String[]{String.valueOf(feedId)});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                imageInfos = new ArrayList<>();
                ImageInfo imageInfo;
                while (!cursor.isAfterLast()) {

                    String imageId = cursor.getString(SQLStatement.ImageInfoTable.IMAGE_ID_INDEX);
                    String urlImage = cursor.getString(SQLStatement.ImageInfoTable.URL_IMAGE_INDEX);
                    int type = cursor.getInt(SQLStatement.ImageInfoTable.TYPE_INDEX);
                    String linkFace = cursor.getString(SQLStatement.ImageInfoTable.LIKE_FACE_INDEX);
                    String urlImageThumbnail = cursor.getString(SQLStatement.ImageInfoTable.URL_IMAGE_THUMBNAIL_INDEX);
                    imageInfo = new ImageInfo(imageId, urlImage, type, linkFace, urlImageThumbnail);
                    imageInfos.add(imageInfo);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }
        return imageInfos;
    }

    public static Video getVideoOfFeed(int feedId) {

        Video video = null;
        Cursor cursor = DatabaseOpenHelper.getDbReader().rawQuery(SQLStatement.QUERY_VIDEO, new String[]{String.valueOf(feedId)});
        if (cursor != null) {

            if (cursor.moveToFirst()) {
                String videoId = cursor.getString(SQLStatement.VideoTable.VIDEO_ID_INDEX);
                String urlVideo = cursor.getString(SQLStatement.VideoTable.URL_VIDEO_INDEX);
                int type = cursor.getInt(SQLStatement.VideoTable.TYPE_INDEX);
                String urlVideoThumbnail = cursor.getString(SQLStatement.VideoTable.URL_VIDEO_THUMBNAIL_INDEX);
                video = new Video(videoId, urlVideo, type, urlVideoThumbnail);
            }
            cursor.close();
        }
        return video;
    }

    public static int  getIsLike(int feedId, String accountId) {
        int likeState = isLikeFeed(feedId, accountId);
        return likeState == -1 ? 0 : likeState;
    }

    public static int isLikeFeed(int feedId, String accountId) {

        int likeState = -1;
        if (accountId != null) {
            Cursor cursor = DatabaseOpenHelper.getDbReader().rawQuery(SQLStatement.QUERY_IS_LIKE, new String[]{String.valueOf(feedId), accountId});
            if (cursor != null) {

                if (cursor.moveToFirst()) {
                    likeState = cursor.getInt(1);
                }
                cursor.close();
            }
        }
        return likeState;
    }

    public static int getCountFeed() {

        int count = 0;
        Cursor cursor = DatabaseOpenHelper.getDbReader().rawQuery(SQLStatement.QUERY_COUNT_FEED, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                count =  cursor.getInt(0);
            }
            cursor.close();
        }
        return count;
    }

    public static int getCountImage() {

        int count = 0;
        Cursor cursor = DatabaseOpenHelper.getDbReader().rawQuery(SQLStatement.QUERY_COUNT_IMAGE, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            cursor.close();
        }
        return count;
    }


    public static int getCountVideo() {

        int count = 0;
        Cursor cursor = DatabaseOpenHelper.getDbReader().rawQuery(SQLStatement.QUERY_COUNT_VIDEO, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            cursor.close();
        }
        return count;
    }



    public static void insertNewFeeds(ArrayList<Feed> feeds) {

        if (feeds != null && feeds.size() > 0) {
            for (Feed feed : feeds) {
                insertNewFeed(feed);
            }

            int countFeeds = DatabaseUtil.getCountFeed();
            if (countFeeds > AppConstant.MAX_ROW_FEED_TABLE) {
                int numRowOver = countFeeds - AppConstant.MAX_ROW_FEED_TABLE;
                deleteFeedOver(numRowOver);
            }
        }
    }

    // save new feed
    public static boolean insertNewFeed(Feed feed) {

        // insert to first table. After insert if total row table over 50 then delete last row
        boolean insertFeedSuccess = true;
        if (feed != null) {

            ContentValues values = new ContentValues();
            values.put(SQLStatement.FeedTable.FEED_ID_COL, feed.getFeedId());
            values.put(SQLStatement.FeedTable.TITLE_COL, feed.getTitle());
            values.put(SQLStatement.FeedTable.TIME_COL, feed.getTime());
            values.put(SQLStatement.FeedTable.LIKE_COL, feed.getLike());
            values.put(SQLStatement.FeedTable.COMMENT_COL, feed.getComment());
            values.put(SQLStatement.FeedTable.SCHOOL_COL, feed.getSchool());
            values.put(SQLStatement.FeedTable.MEMBER_ID_COL, feed.getMember().getMemberId());
            if (DatabaseOpenHelper.getDbWriter().insert(SQLStatement.FeedTable.TABLE_NAME, null, values) == -1) {
                insertFeedSuccess = false;
            }

            // insert image or video and member
            if (feed.getImages() != null && feed.getImages().size() > 0) {
                if (!insertImages(feed.getImages(), feed.getFeedId())) {
                    insertFeedSuccess = false;
                }
            } else if (feed.getVideo() != null) {
                if (!insertVideo(feed.getVideo(), feed.getFeedId())) {
                    insertFeedSuccess = false;
                }
            }
            if (!insertBaseInfoMember(feed.getMember())) {
                insertFeedSuccess = false;
            }
        }

        return insertFeedSuccess;
    }

    public static boolean insertImages(ArrayList<ImageInfo> images, int feedId) {

        ContentValues values;
        boolean insertSuccess = true;
        for (ImageInfo imageInfo : images) {

            values = new ContentValues();
            values.put(SQLStatement.FeedTable.FEED_ID_COL, feedId);
            values.put(SQLStatement.ImageInfoTable.IMAGE_ID_COL, imageInfo.getImageId());
            values.put(SQLStatement.ImageInfoTable.URL_IMAGE_COL, imageInfo.getUrlImage());
            values.put(SQLStatement.ImageInfoTable.TYPE_COL, imageInfo.getType());
            values.put(SQLStatement.ImageInfoTable.LINK_FACE_COL, imageInfo.getLinkFace());
            values.put(SQLStatement.ImageInfoTable.URL_IMAGE_THUMBNAIL_COL, imageInfo.getUrlImageThumbnail());
            if (DatabaseOpenHelper.getDbWriter().insert(SQLStatement.ImageInfoTable.TABLE_NAME, null, values) == -1) {
                insertSuccess = false;
            }
        }
        return insertSuccess;
    }

    public static boolean insertVideo(Video video, int feedId) {

        boolean insertSuccess = false;
        if (video != null) {
            ContentValues values = new ContentValues();
            values.put(SQLStatement.FeedTable.FEED_ID_COL, feedId);
            values.put(SQLStatement.VideoTable.VIDEO_ID_COL, video.getVideoId());
            values.put(SQLStatement.VideoTable.URL_VIDEO_COL, video.getUrlVideo());
            values.put(SQLStatement.VideoTable.TYPE_COL, video.getType());
            values.put(SQLStatement.VideoTable.URL_VIDEO_THUMBNAIL_COL, video.getUrlVideoThumbnail());

            if (DatabaseOpenHelper.getDbWriter().insert(SQLStatement.VideoTable.TABLE_NAME, null, values) != -1) {
                insertSuccess = true;
            }
        }

        return insertSuccess;
    }

    public static boolean updateLikeState(int afterLikeState,  int feedId, String accountId) {

        ContentValues values = new ContentValues();
        values.put(SQLStatement.MyActivityInfoTable.IS_LIKE_COL, afterLikeState);
        int numRowUpdate = DatabaseOpenHelper.getDbWriter().update(SQLStatement.MyActivityInfoTable.TABLE_NAME, values,
                SQLStatement.WHERE_CLAUSE_UPDATE_LIKE_STATE,
                new String [] {String.valueOf(feedId), accountId});
        return numRowUpdate != 0;
    }

    public static boolean insertLikeState(int afterLikeState, int feedId, String accountId) {

        ContentValues values = new ContentValues();
        values.put(SQLStatement.FeedTable.FEED_ID_COL, feedId);
        values.put(SQLStatement.MemberTable.MEMBER_ID_COL, accountId);
        values.put(SQLStatement.MyActivityInfoTable.IS_LIKE_COL, afterLikeState);
        long idRowInsert = DatabaseOpenHelper.getDbWriter().insert(SQLStatement.MyActivityInfoTable.TABLE_NAME, null, values);
        return idRowInsert != -1;
    }

    public static boolean updateNumLike(int feedId, int afterNumLike) {

        ContentValues values = new ContentValues();
        values.put(SQLStatement.FeedTable.LIKE_COL, afterNumLike);
        int numRowUpdate = DatabaseOpenHelper.getDbWriter().update(SQLStatement.FeedTable.TABLE_NAME, values,
                SQLStatement.WHERE_CLAUSE_UPDATE_NUM_LIKE, new String[] {String.valueOf(feedId)});
        return numRowUpdate != 0;
    }

    public static boolean insertMember(Member member) {

        boolean insertSuccess = false;
        if (member != null) {

            ContentValues values = new ContentValues();
            values.put(SQLStatement.MemberTable.MEMBER_ID_COL, member.getMemberId());
            values.put(SQLStatement.MemberTable.USERNAME_COL, member.getUsername());
            values.put(SQLStatement.MemberTable.RANK_COL, member.getRank());
            values.put(SQLStatement.MemberTable.LIKE_COL, member.getLike());
            values.put(SQLStatement.MemberTable.AVATAR_URL_COL, member.getAvatarUrl());
            values.put(SQLStatement.MemberTable.TOTAL_IMAGE_COL, member.getTotalImage());

            if (DatabaseOpenHelper.getDbWriter().insert(SQLStatement.MemberTable.TABLE_NAME, null, values) != -1) {
                insertSuccess = true;
            }
        }
        return insertSuccess;
    }

    public static boolean insertBaseInfoMember(BaseInfoMember baseInfoMember) {

        boolean insertSuccess = false;
        if (baseInfoMember != null) {

            ContentValues values = new ContentValues();
            values.put(SQLStatement.MemberTable.MEMBER_ID_COL, baseInfoMember.getMemberId());
            values.put(SQLStatement.MemberTable.USERNAME_COL, baseInfoMember.getUsername());
            values.put(SQLStatement.MemberTable.AVATAR_URL_COL, baseInfoMember.getAvatarUrl());

            if (DatabaseOpenHelper.getDbWriter().insert(SQLStatement.MemberTable.TABLE_NAME, null, values) != -1) {
                insertSuccess = true;
            }
        }
        return insertSuccess;
    }

    private static boolean deleteFeedOver(int numFeedOver) {

        int rowEffect = DatabaseOpenHelper.getDbWriter().delete(SQLStatement.FeedTable.TABLE_NAME,
                SQLStatement.getWhereClauseDeleteFeed(numFeedOver), null);
        return rowEffect != numFeedOver;
    }
}
