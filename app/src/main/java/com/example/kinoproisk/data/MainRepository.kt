package com.example.kinoproisk.data

import com.example.kinoproisk.R
import com.example.kinoproisk.domain.Film

class MainRepository {
    val filmsDataBase = listOf(
        Film("Brokeback Mountain", R.drawable.brokeback_mountain, "Ennis and Jack are two shepherds who develop a sexual and emotional relationship. Their relationship becomes complicated when both of them get married to their respective girlfriends.", 6.9f),
        Film("My Own Private Idaho", R.drawable.aida, "Two best friends living on the streets of Portland as hustlers embark on a journey of self discovery and find their relationship stumbling along the way.", 8.8f),
        Film("Pride", R.drawable.pride, "U.K. gay activists work to help miners during their lengthy strike of the National Union of Mineworkers in the summer of 1984.", 7.7f),
        Film("Milk", R.drawable.milk, "The story of Harvey Milk and his struggles as an American gay activist who fought for gay rights and became California's first openly gay elected official.",9.9f),
        Film("A Single Man", R.drawable.lonely, "An English professor, one year after the sudden death of his boyfriend, is unable to cope with his typical days in 1960s Los Angeles.", 6.7f),
        Film("I Love You Phillip Morris", R.drawable.phil, "A cop turns con man once he comes out of the closet. Once imprisoned, he meets the second love of his life, whom he'll stop at nothing to be with.", 8.8f),
        Film("Philadelphia", R.drawable.philadel, "When a man with HIV is fired by his law firm because of his condition, he hires a homophobic small time lawyer as the only willing advocate for a wrongful dismissal suit.", 8.1f),
        Film("Bohemian Rhapsody", R.drawable.rapsody, "The story of the legendary British rock band Queen and lead singer Freddie Mercury, leading up to their famous performance at Live Aid (1985).", 9.1f),
        Film("Tom at the Farm", R.drawable.tom, "A grieving man meets his lover's family, who were not aware of their son's sexual orientation.", 5.5f)
    )
}