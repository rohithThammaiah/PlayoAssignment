package me.assignment.playo.newsapp.di

import dagger.Component
import me.assignment.playo.newsapp.di.scopes.ApplicationScope

@ApplicationScope
@Component(modules = [])
interface PlayoNewsAppComponent {
}