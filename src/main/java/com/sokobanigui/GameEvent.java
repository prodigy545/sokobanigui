package com.sokobanigui;

public record GameEvent(GameEventType type, Position from, Position to, String note) {};