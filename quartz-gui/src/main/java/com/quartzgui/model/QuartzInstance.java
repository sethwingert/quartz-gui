package com.quartzgui.model;

/**
 * Represents one Quartz Instance. A server can have more than one
 * QuartzInstance. One Quartz Instance can have many Schedulers. This object is
 * kept in mem for easy fetching, thus avoiding having to instantiate a JMX
 * connection for every call.
 */
public class QuartzInstance {

}
