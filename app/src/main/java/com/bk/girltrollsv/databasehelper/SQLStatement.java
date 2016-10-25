package com.bk.girltrollsv.databasehelper;

import com.bk.girltrollsv.constant.AppConstant;

/**
 * Created by Dell on 16-Sep-16.
 */
public class SQLStatement {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "GirlTrollSv.db";
    public static final String CONSTRAINT_DELETE_UPDATE = "ON DELETE CASCADE ON UPDATE CASCADE";
    public static final String ORDER_BY = "ORDER BY";
    public static final String LIMIT = "LIMIT";
    public static final String OFFSET = "OFFSET";

    public static class FeedTable {

        public static final String TABLE_NAME = "Feed";
        public static final String FEED_ID_COL = "feedId";
        public static final String TITLE_COL = "title";
        public static final String TIME_COL = "time";
        public static final String LIKE_COL = "like";
        public static final String COMMENT_COL = "comment";
        public static final String SCHOOL_COL = "school";
        public static final String MEMBER_ID_COL = "memberId";

        public static final int FEED_ID_INDEX = 0;
        public static final int TITLE_INDEX = 1;
        public static final int TIME_INDEX = 2;
        public static final int LIKE_INDEX = 3;
        public static final int COMMENT_INDEX= 4;
        public static final int SCHOOL_INDEX = 5;
        public static final int MEMBER_ID_INDEX = 6;


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + "(" +
                FEED_ID_COL + " INTEGER NOT NULL PRIMARY KEY," +
                TITLE_COL + " TEXT NOT NULL," +
                TIME_COL + " TEXT NOT NULL," +
                LIKE_COL + " INTEGER," +
                COMMENT_COL + " INTEGER," +
                SCHOOL_COL + " TEXT," +
                MEMBER_ID_COL + " TEXT NOT NULL REFERENCES " +
                MemberTable.TABLE_NAME + "(" +
                MemberTable.MEMBER_ID_COL + ") " +
                CONSTRAINT_DELETE_UPDATE + ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    public static class MemberTable {

        public static final String TABLE_NAME = "Member";
        public static final String MEMBER_ID_COL = "memberId";
        public static final String USERNAME_COL = "username";
        public static final String RANK_COL = "rank";
        public static final String LIKE_COL = "like";
        public static final String AVATAR_URL_COL = "avatarUrl";
        public static final String TOTAL_IMAGE_COL = "totalImage";

        public static final int MEMBER_ID_INDEX = 0;
        public static final int USERNAME_INDEX = 1;
        public static final int RANK_INDEX = 2;
        public static final int LIKE_INDEX = 3;
        public static final int AVATAR_URL_INDEX = 4;
        public static final int TOTAL_IMAGE_INDEXX = 5;


        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + "(" +
                MEMBER_ID_COL + " TEXT NOT NULL PRIMARY KEY," +
                USERNAME_COL + " TEXT NOT NULL," +
                RANK_COL + " FLOAT," +
                LIKE_COL + " INTEGER," +
                AVATAR_URL_COL + " TEXT," +
                TOTAL_IMAGE_COL + " INTEGER);";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    public static class ImageInfoTable {

        public static final String TABLE_NAME = "ImageInfo";
        public static final String IMAGE_ID_COL = "imageId";
        public static final String URL_IMAGE_COL = "urlImage";
        public static final String TYPE_COL = "type";
        public static final String LINK_FACE_COL = "linkFace";
        public static final String URL_IMAGE_THUMBNAIL_COL = "urlImageThumbnail";

        public static final int IMAGE_ID_INDEX = 0;
        public static final int URL_IMAGE_INDEX = 1;
        public static final int TYPE_INDEX = 2;
        public static final int LIKE_FACE_INDEX = 3;
        public static final int URL_IMAGE_THUMBNAIL_INDEX = 4;
        public static final int FEED_ID_INDEX = 5;




        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + "(" +
                IMAGE_ID_COL + " TEXT NOT NULL PRIMARY KEY," +
                URL_IMAGE_COL + " TEXT NOT NULL," +
                TYPE_COL + " INTEGER NOT NULL," +
                LINK_FACE_COL + " TEXT," +
                URL_IMAGE_THUMBNAIL_COL + " TEXT NOT NULL," +
                FeedTable.FEED_ID_COL + " INTEGER NOT NULL REFERENCES " +
                FeedTable.TABLE_NAME + "(" +
                FeedTable.FEED_ID_COL + ") " +
                CONSTRAINT_DELETE_UPDATE + ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    public static class VideoTable {

        public static final String TABLE_NAME = "Video";
        public static final String VIDEO_ID_COL = "videoId";
        public static final String URL_VIDEO_COL = "urlVideo";
        public static final String TYPE_COL = "type";
        public static final String URL_VIDEO_THUMBNAIL_COL = "urlVideoThumbnail";

        public static final int VIDEO_ID_INDEX = 0;
        public static final int URL_VIDEO_INDEX = 1;
        public static final int TYPE_INDEX = 2;
        public static final int URL_VIDEO_THUMBNAIL_INDEX = 3;
        public static final int FEED_ID_INDEX = 4;

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + "(" +
                VIDEO_ID_COL + " TEXT NOT NULL PRIMARY KEY," +
                URL_VIDEO_COL + " TEXT NOT NULL," +
                TYPE_COL + " INTEGER NOT NULL," +
                URL_VIDEO_THUMBNAIL_COL + " TEXT NOT NULL," +
                FeedTable.FEED_ID_COL + " INTEGER NOT NULL REFERENCES " +
                FeedTable.TABLE_NAME + "(" +
                FeedTable.FEED_ID_COL + ") " +
                CONSTRAINT_DELETE_UPDATE + ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    public static class FeedCommentTable {

        public static final String TABLE_NAME = "FeedComment";
        public static final String COMMENT_ID_COL = "commentId";
        public static final String COMMENT_COL = "comment";
        public static final String TIME_COL = "time";

        public static final int COMMENT_ID_INDEX = 0;
        public static final int COMMENT_INDEX = 1;
        public static final int TIME_INDEX = 2;
        public static final int FEED_ID_INDEX = 3;
        public static final int MEMBER_ID_INDEX = 4;

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + "(" +
                COMMENT_ID_COL + " TEXT NOT NULL PRIMARY KEY," +
                COMMENT_COL + " TEXT NOT NULL," +
                TIME_COL + " TEXT NOT NULL," +
                FeedTable.FEED_ID_COL + " INTEGER NOT NULL," +
                MemberTable.MEMBER_ID_COL + " TEXT NOT NULL," +
                "CONSTRAINT fk_feedId FOREIGN KEY (" + FeedTable.FEED_ID_COL + ")" +
                " REFERENCES " + FeedTable.TABLE_NAME +
                "(" + FeedTable.FEED_ID_COL + ") "
                + CONSTRAINT_DELETE_UPDATE + "," +
                "CONSTRAINT fk_memberId FOREIGN KEY (" + MemberTable.MEMBER_ID_COL + ")" +
                " REFERENCES " + MemberTable.TABLE_NAME +
                "(" + MemberTable.MEMBER_ID_COL + "));";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    public static class MyActivityInfoTable {

        public static final String TABLE_NAME = "MyActivityInfo";
        public static final String IS_LIKE_COL = "isLike"; // index 2

        public static final int MEMBER_ID_INDEX = 0;
        public static final int FEED_ID_INDEX = 1;
        public static final int IS_LIKE_INDEX = 2;

        public static final String CREATE_TABLE = "CREATE TABLE " +
                TABLE_NAME + "(" +
                MemberTable.MEMBER_ID_COL + " TEXT NOT NULL," +
                FeedTable.FEED_ID_COL + " INTEGER NOT NULL," +
                IS_LIKE_COL + " SMALLINT NOT NULL," +
                "PRIMARY KEY (" + MemberTable.MEMBER_ID_COL + "," +
                FeedTable.FEED_ID_COL + ")," +
                "CONSTRAINT fk_feedId FOREIGN KEY (" + FeedTable.FEED_ID_COL + ")" +
                " REFERENCES " + FeedTable.TABLE_NAME +
                "(" + FeedTable.FEED_ID_COL + ") "
                + CONSTRAINT_DELETE_UPDATE + ");";

        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

    // fetch all feed, order by feedId
    public static final String QUERY_ALL_FEED = "SELECT " +
            FeedTable.TABLE_NAME + "." + FeedTable.FEED_ID_COL + "," +
            FeedTable.TABLE_NAME + "." + FeedTable.TITLE_COL + "," +
            FeedTable.TABLE_NAME + "." + FeedTable.TIME_COL + "," +
            FeedTable.TABLE_NAME + "." + FeedTable.LIKE_COL + "," +
            FeedTable.TABLE_NAME + "." + FeedTable.COMMENT_COL + "," +
            FeedTable.TABLE_NAME + "." + FeedTable.SCHOOL_COL + "," +
            MemberTable.TABLE_NAME + "." + MemberTable.MEMBER_ID_COL + "," +
            MemberTable.TABLE_NAME + "." + MemberTable.USERNAME_COL + "," +
            MemberTable.TABLE_NAME + "." + MemberTable.AVATAR_URL_COL + " " +
            "FROM " + FeedTable.TABLE_NAME + "," + MemberTable.TABLE_NAME + " " +
            "WHERE " + FeedTable.TABLE_NAME + "." + FeedTable.MEMBER_ID_COL + "=" +
            MemberTable.TABLE_NAME + "." + MemberTable.MEMBER_ID_COL + " " +
            ORDER_BY + " " + FeedTable.FEED_ID_COL + " DESC";


    public static final String QUERY_IMAGE_INFO = "SELECT * FROM " + ImageInfoTable.TABLE_NAME +
            " WHERE " + FeedTable.FEED_ID_COL + " = ?";

    public static final String QUERY_VIDEO = "SELECT * FROM " + VideoTable.TABLE_NAME +
            " WHERE " + FeedTable.FEED_ID_COL + " = ?";

    public static final String QUERY_IS_LIKE = "SELECT * FROM " + MyActivityInfoTable.TABLE_NAME +
            " WHERE " + FeedTable.FEED_ID_COL + " = ? and " + MemberTable.MEMBER_ID_COL + " = ?";

    public static String getQueryFeedOffset(int limit, int rowOffset) {

        String queryLimitFeed = "SELECT " +
                FeedTable.TABLE_NAME + "." + FeedTable.FEED_ID_COL + "," +
                FeedTable.TABLE_NAME + "." + FeedTable.TITLE_COL + "," +
                FeedTable.TABLE_NAME + "." + FeedTable.TIME_COL + "," +
                FeedTable.TABLE_NAME + "." + FeedTable.LIKE_COL + "," +
                FeedTable.TABLE_NAME + "." + FeedTable.COMMENT_COL + "," +
                FeedTable.TABLE_NAME + "." + FeedTable.SCHOOL_COL + "," +
                MemberTable.TABLE_NAME + "." + MemberTable.MEMBER_ID_COL + "," +
                MemberTable.TABLE_NAME + "." + MemberTable.USERNAME_COL + "," +
                MemberTable.TABLE_NAME + "." + MemberTable.AVATAR_URL_COL + " " +
                "FROM " + FeedTable.TABLE_NAME + "," + MemberTable.TABLE_NAME + " " +
                "WHERE " + FeedTable.TABLE_NAME + "." + FeedTable.MEMBER_ID_COL + "=" +
                MemberTable.TABLE_NAME + "." + MemberTable.MEMBER_ID_COL + " " +
                ORDER_BY + " " + FeedTable.FEED_ID_COL + " DESC " + LIMIT +
                " " + limit + " " + OFFSET + " " + rowOffset;

        return queryLimitFeed;
    }

    public static String getQueryFeedPaging(int limit, int startFeedId) {

        String queryFeedPaging = "SELECT " +
                FeedTable.TABLE_NAME + "." + FeedTable.FEED_ID_COL + "," +
                FeedTable.TABLE_NAME + "." + FeedTable.TITLE_COL + "," +
                FeedTable.TABLE_NAME + "." + FeedTable.TIME_COL + "," +
                FeedTable.TABLE_NAME + "." + FeedTable.LIKE_COL + "," +
                FeedTable.TABLE_NAME + "." + FeedTable.COMMENT_COL + "," +
                FeedTable.TABLE_NAME + "." + FeedTable.SCHOOL_COL + "," +
                MemberTable.TABLE_NAME + "." + MemberTable.MEMBER_ID_COL + "," +
                MemberTable.TABLE_NAME + "." + MemberTable.USERNAME_COL + "," +
                MemberTable.TABLE_NAME + "." + MemberTable.AVATAR_URL_COL  +
                " FROM " + FeedTable.TABLE_NAME + "," + MemberTable.TABLE_NAME +
                " WHERE " + FeedTable.FEED_ID_COL + "<" + startFeedId + " and " +
                FeedTable.TABLE_NAME + "." + FeedTable.MEMBER_ID_COL + "=" +
                MemberTable.TABLE_NAME + "." + MemberTable.MEMBER_ID_COL + " " +
                ORDER_BY + " " + FeedTable.FEED_ID_COL + " DESC " + LIMIT +
                " " + limit;

        return queryFeedPaging;
    }

    public static final String QUERY_COUNT_FEED = "SELECT count(*) FROM " + FeedTable.TABLE_NAME;

    public static String getWhereClauseDeleteFeed(int limit) {

        String where = FeedTable.FEED_ID_COL + " in ( select " + FeedTable.FEED_ID_COL +
                " from " + FeedTable.TABLE_NAME + " " + ORDER_BY + " " + FeedTable.FEED_ID_COL +
                " " + LIMIT + " " + limit + ")";
        return where;
    }

    public static final String WHERE_CLAUSE_UPDATE_LIKE_STATE = FeedTable.FEED_ID_COL  + " = ? and " +
            MemberTable.MEMBER_ID_COL + " = ?";

    public static final String WHERE_CLAUSE_UPDATE_NUM_LIKE = FeedTable.FEED_ID_COL + " = ?";

    public static final String TRIGGER_DELETE_OVER_ROW_FEED = "create trigger delete_feed_over " +
            "after insert on " + FeedTable.TABLE_NAME + " for each row when (select count(*) from " +
            FeedTable.TABLE_NAME + ") > " + AppConstant.MAX_ROW_FEED_TABLE + " begin " +
            "delete from " + FeedTable.TABLE_NAME + " where " + FeedTable.FEED_ID_COL +
            " in (select " + FeedTable.FEED_ID_COL + " from " + FeedTable.TABLE_NAME +
            " " + ORDER_BY + " " + FeedTable.FEED_ID_COL + " " + LIMIT + "(select count(*) from " +
            FeedTable.TABLE_NAME + ") - " + AppConstant.MAX_ROW_FEED_TABLE + "); end";

    public static final String  TRIGGER_DELETE_VIDEO = "create trigger if not exists delete_video after delete on " +
            FeedTable.TABLE_NAME + " for each row begin delete from " + VideoTable.TABLE_NAME +
            " where " + FeedTable.FEED_ID_COL + " = old." + FeedTable.FEED_ID_COL + ";end";

    public static final String  TRIGGER_DELETE_IMAGE = "create trigger if not exists delete_image after delete on " +
            FeedTable.TABLE_NAME + " for each row begin delete from " + ImageInfoTable.TABLE_NAME +
            " where " + FeedTable.FEED_ID_COL + " = old." + FeedTable.FEED_ID_COL + ";end";

    public static final String  TRIGGER_DELETE_FEED_COMMENT = "create trigger if not exists delete_feed_comment after delete on " +
            FeedTable.TABLE_NAME + " for each row begin delete from " + FeedCommentTable.TABLE_NAME +
            " where " + FeedTable.FEED_ID_COL + " = old." + FeedTable.FEED_ID_COL + ";end";

    public static final String  TRIGGER_DELETE_MY_ACTIVITY_INFO = "create trigger if not exists delete_my_activity_info after delete on " +
            FeedTable.TABLE_NAME + " for each row begin delete from " + FeedCommentTable.TABLE_NAME +
            " where " + FeedTable.FEED_ID_COL + " = old." + FeedTable.FEED_ID_COL + ";end";


    public static final String QUERY_COUNT_IMAGE = "select count(*) from " + ImageInfoTable.TABLE_NAME;

    public static final String QUERY_COUNT_VIDEO = "select count(*) from " + VideoTable.TABLE_NAME;
}
