![Lithium Logo](https://i.imgur.com/LDEckzI.png "Lithium logo")

---
# Lithium Framework
Lithium is an UI Framework for Spigot servers. Lithium makes use of a **client mod** to allow plugin developers to create cool looking interfaces.

## Lithium Window
All interfaces shown to the player, are created using a `LWindow` (Lithium Window).

`LWindow` is an object that extends `LContainer`. `LContainer` is an abstraction to containers (used in `LWindow` and `LPanel`).

## Lithium Controls
In Lithium, any UI must have components. Those components are called controls.
Any control must extend the class `LControl`. This class is responsible for mantaining some useful internal properties such as `Text`(for controls that support it) and `UUID`.
All properties can be changed on the server, and the controls will be updated on the client(it's called **HotSwap**).

## Lithium in action
![Photo](https://i.imgur.com/PHT7HRg.gif "Lithium's HotSwap feature")


[Original thread on SpigotMc](https://www.spigotmc.org/threads/lithium.274569/)
