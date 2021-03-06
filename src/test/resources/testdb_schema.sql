CREATE SCHEMA IF NOT EXISTS STATICGTFS;

SET search_path TO staticgtfs;

CREATE TABLE ENUM_VALUES
(
    ID          SERIAL PRIMARY KEY,
    FILE        VARCHAR(50)  NOT NULL,
    FIELD       VARCHAR(50)  NOT NULL,
    CODE        VARCHAR(50)  NOT NULL,
    NAME        VARCHAR(100) NOT NULL,
    DESCRIPTION VARCHAR(200),
    constraint ENUM_VALUES_UN unique (FILE, FIELD, CODE, NAME)
);

CREATE TABLE AGENCIES
(
    ID        SERIAL PRIMARY KEY,
    AGENCY_ID VARCHAR(100) NOT NULL,
    NAME      VARCHAR(255) NOT NULL,
    URL       VARCHAR(255),
    TIMEZONE  VARCHAR(100),
    LANGUAGE  VARCHAR(100),
    PHONE     VARCHAR(100),
    FARE_URL  VARCHAR(100),
    EMAIL     VARCHAR(100),
    constraint AGENCIES_UN unique (AGENCY_ID)
);

CREATE TABLE STOPS
(
    ID                  SERIAL PRIMARY KEY,
    STOP_ID             VARCHAR(255),
    CODE                VARCHAR(50),
    NAME                VARCHAR(255)   NOT NULL,
    DESCRIPTION         VARCHAR(255),
    LATITUDE            DECIMAL(10, 6) NOT NULL,
    LONGITUDE           DECIMAL(10, 6) NOT NULL,
    ZONE_ID             VARCHAR(255),
    URL                 VARCHAR(255),
    LOCATION_TYPE       VARCHAR(50),
    PARENT_STATION      VARCHAR(100),
    TIMEZONE            VARCHAR(50),
    WHEELCHAIR_BOARDING VARCHAR(50),
    LEVEL_ID            VARCHAR(100),
    PLATFORM_CODE       VARCHAR(100),
    constraint STOPS_UN unique (STOP_ID)
);

CREATE TABLE ROUTES
(
    ID          SERIAL PRIMARY KEY,
    ROUTE_ID    VARCHAR(100) NOT NULL,
    AGENCY_ID   VARCHAR(50)  NOT NULL,
    SHORT_NAME  VARCHAR(50)  NOT NULL,
    LONG_NAME   VARCHAR(255) NOT NULL,
    DESCRIPTION VARCHAR(255),
    ROUTE_TYPE  VARCHAR(50),
    TEXT_COLOR  VARCHAR(255),
    ROUTE_COLOR VARCHAR(255),
    URL         VARCHAR(255),
    SORT_ORDER  INT,
    constraint ROUTES_UN unique (ROUTE_ID),
    constraint ROUTES_FK foreign key (AGENCY_ID) references AGENCIES (AGENCY_ID)
);

CREATE TABLE SHAPES
(
    ID            SERIAL PRIMARY KEY,
    SHAPE_ID      VARCHAR(255),
    LATITUDE      DECIMAL(10, 6) NOT NULL,
    LONGITUDE     DECIMAL(10, 6) NOT NULL,
    SEQUENCE      BIGINT         NOT NULL,
    DIST_TRAVELED DECIMAL(10, 6) NULL,
    constraint SHAPES_UN unique (SHAPE_ID, SEQUENCE)
);

CREATE TABLE CALENDARS
(
    ID         SERIAL PRIMARY KEY,
    SERVICE_ID VARCHAR(255) NOT NULL,
    MONDAY     SMALLINT     NOT NULL,
    TUESDAY    SMALLINT     NOT NULL,
    WEDNESDAY  SMALLINT     NOT NULL,
    THURSDAY   SMALLINT     NOT NULL,
    FRIDAY     SMALLINT     NOT NULL,
    SATURDAY   SMALLINT     NOT NULL,
    SUNDAY     SMALLINT     NOT NULL,
    START_DATE VARCHAR(8),
    END_DATE   VARCHAR(8),
    constraint CALENDARS_UN unique (SERVICE_ID)
);

CREATE TABLE TRIPS
(
    ID                    SERIAL PRIMARY KEY,
    ROUTE_ID              VARCHAR(100) NOT NULL,
    SERVICE_ID            VARCHAR(100) NOT NULL,
    TRIP_ID               VARCHAR(255) NOT NULL,
    HEADSIGN              VARCHAR(50),
    SHORT_NAME            VARCHAR(100),
    DIRECTION_ID          VARCHAR(50),
    BLOCK_ID              VARCHAR(100),
    SHAPE_ID              VARCHAR(100),
    WHEELCHAIR_ACCESSIBLE VARCHAR(50),
    BIKES_ALLOWED         VARCHAR(50),
    constraint TRIPS_TRIP_UN unique (TRIP_ID),
    constraint TRIPS_ROUTE_FK foreign key (ROUTE_ID) references ROUTES (ROUTE_ID),
    constraint TRIPS_SERVICE_FK foreign key (SERVICE_ID) references CALENDARS (SERVICE_ID)
);

CREATE TABLE STOPTIMES
(
    ID                      SERIAL PRIMARY KEY,
    TRIP_ID                 VARCHAR(100) NOT NULL,
    ARRIVAL_TIME            VARCHAR(50)  NOT NULL,
    DEPARTURE_TIME          VARCHAR(50)  NOT NULL,
    STOP_ID                 VARCHAR(100) NOT NULL,
    SEQUENCE                BIGINT       NOT NULL,
    HEADSIGN                VARCHAR(50),
    PICKUP_TYPE             VARCHAR(50),
    DROP_OFF_TYPE           VARCHAR(50),
    SHAPE_DISTANCE_TRAVELED DECIMAL(10, 6),
    TIMEPOINT               VARCHAR(50),
    constraint STOPTIMES_TRIP_FK foreign key (TRIP_ID) references TRIPS (TRIP_ID),
    constraint STOPTIMES_STOP_FK foreign key (STOP_ID) references STOPS (STOP_ID)
);

CREATE TABLE FREQUENCIES
(
    ID           SERIAL PRIMARY KEY,
    TRIP_ID      VARCHAR(100) NOT NULL,
    START_TIME   VARCHAR(50)  NOT NULL,
    END_TIME     VARCHAR(50)  NOT NULL,
    HEADWAY_SECS BIGINT       NOT NULL,
    EXACT_TIMES  VARCHAR(2),
    constraint FREQUENCIES_TRIP_FK foreign key (TRIP_ID) references TRIPS (TRIP_ID)
);