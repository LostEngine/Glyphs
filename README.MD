# Glyphs,
## A Lightweight Spigot plugin to create glyphs using a resource pack by Misieur

## How to create new glyphs
To create new glyphs, you first want to find the `glyphs.yml` file in the plugin folder and open it.
You can create new glyphs by adding a new entry and adding the required fields.
Here is an example of a glyph configuration:
```yaml
example_glyph:
  image_path: "font/example_image" # the .png is not required
  height: 8
  ascent: 7
```
Then you can put your image in the `textures` folder with the same path as specified in the `image_path` field.
You can now use the `/glyphs reload` command to rebuild the resource pack.

## How to use glyphs in-game
You first need to make sure your players have the resource pack installed on their clients.
Once this is done there are multiple ways to use glyphs in your server.
The way this plugin works is that instead of using static Unicode characters, it uses translations,
this makes so the client knows which glyph is which character instead of mapping them server side.

---
Using minecraft commands
You can use the `/tellraw` command to send glyphs to players.
Here is an example of how to send a glyph using the `tellraw` command:
```java
/tellraw @p {"translate":"glyph.example_glyph"}
```

---
Using the minimessage format
For plugins that support the minimessage format, you can use the following syntax to display glyphs:
###### (no fallback)
```html
<tr:glyph.example_glyph>
```

###### (with fallback)
```html
<tr_or:glyph.example_glyph:'Emoji Not Found'>
```

---
## Support

[![](https://dcbadge.limes.pink/api/server/https://discord.gg/5VSeDcyJt7)](https://discord.gg/5VSeDcyJt7)

---
## Additional informations

---
### Ranks
To display glyph ranks using the TAB plugin, you have to create a luckperms group and edit the `plugins/TAB/groups.yml` file to add the following configuration:
```yaml
group_name:
  tabprefix: "<tr:glyph.glyph_name>"
```

---
### Offsets
To use text offsets, you can use `offsets.number` with numbers between _from_ and _to_ from the `config.yml` file (-1023 to 1024 by default).[Glyphs-1.0-SNAPSHOT.jar](build/libs/Glyphs-1.0-SNAPSHOT.jar)

examples:
```java
/tellraw @p {"translate":"offsets.51"}
```
```html
<tr:offsets.-40>
```