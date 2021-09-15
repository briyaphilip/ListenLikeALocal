# Listen Like a Local

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
Listen Like a Local is an app that will use the Spotify API and the MediaWiki API to allow users to discover music and learn about the top artists for any country in the world.

<img src='https://media.giphy.com/media/WPkAe4OEtaJG87eFRO/giphy.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User can explore and Spotify playlists after entering in the country code
* User can save Spotify playlists 
* User can explore artist info

**Optional Nice-to-have Stories**

* User can view travel history on a map
* User can hear song when clicked
* User can explore information about the music in that country 
* Profile page where user can see location history and saved playlistsUser can filter playlists by mood/danceability

### 2. Screen Archetypes

* Login
   * connect to Spotify
* Stream
    * view of locations user has visited/ generated playlists for
    * user can click on location to reveal playlist
* Location Input
   * put in country that you are in/ will be traveling to
* Location Overview
   * overview of what genres/songs/artists are most common in location (map view) 
   * info from Wiki api
* Playlist View
   * user can now see playlists and can listen to the playlist

## Schema 
[This section will be completed in Unit 9]
### Models
Location Input Model

| Property | Type    | Description|
| -------- | -------- | -------- |
| location     | String     | name of place user would like to discover music for     |

Stream Model 


| Property | Type    | Description |
| -------- | -------- | -------- |
| playlistCover     | File   | image of featred playlists for each country   |
| countryName     | String     | name of country     |

Playlist Detail Model


| Property | Type    | Description |
| -------- | -------- | -------- |
| artistPic     | File     | profile of artist     |
| artistName    | String     | name of artist      |
| songName    | String     | name of songs in playlist     |

Artist Detail Model


| Property | Type    | Description |
| -------- | -------- | -------- |
| artistPic     | File     | profile of artist     |
| artistName    | String     | name of artist      |
| artistDescription    | String     | info about artist     |



### Networking
**Home Stream** 
* (READ/GET) Query countries visited from database
* (READ/GET) Featured playlists cover and name from Spotify API endpoint

**Add Loction**
* (READ/GET) Retreive playlists from Spotify API

**Playlist Detail**
* (READ/GET) Query playlist cover from Spotify API endpoint 
* (READ/GET) Query playlist name from Spotify API endpoint 
* (READ/GET) Query playlist items from Spotify API endpoint 

**Playlist Detail**
* (READ/GET) Query artist image from Spotify API endpoint 
* (READ/GET) Query artist name from Spotify API endpoint 
* (READ/GET) Query artist description from MediaWiki API endpoint 
