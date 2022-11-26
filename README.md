# OpenSkyWars

An Open-source 1.19.2 SkyWars plugin with features such as configurable kits and custom games with modifiers.

## Features

- Fully customisable loot levels that change between island and mid
- Fully customisable kits
- Custom private games with movement modifiers, events, loot levels, etc.
- Queue system (currently broken)
- Customisable chest and spawn locations

## Dependencies

- Multiverse
- Citizens

## Usage

### Setting up the plugin

- Drop the latest jar into your server's plugin directory.
- Restart the server
- Go to the position where your lobby is and run <code>/setlobby</code>. This will be the location players are teleported to after a game is finished or when running <code>/skywars lobby</code>.
- Add an NPC near the lobby position with the name SkyWars. This NPC will be how players access public games.
- Teleport to every world you want as a map and run <code>/addworld</code>.
- Restart the server
- Set up each world and edit the configuration files to customise everything. Take a look at the wiki for a detailed explanation of each option.

### Setting up each world

- Look at an island chest and run <code>/addislandchest</code>. Repeat for every island chest.
- Look at a mid chest and run <code>/addmidchest</code>. Repeat for every mid chest.
- Go to every island and run <code>/addspawn</code>. Players will spawn in a cage a few blocks above this position so make sure there is space for that.

## Planned features

- Language file
- Pre-game lobby
