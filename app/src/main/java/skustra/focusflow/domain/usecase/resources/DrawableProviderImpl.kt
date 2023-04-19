package skustra.focusflow.domain.usecase.resources

import skustra.focusflow.R

class DrawableProviderImpl : DrawableProvider {

    override fun getPauseIcon() = R.drawable.ic_pause

    override fun getResumeIcon() = R.drawable.ic_play

    override fun getPlayIcon() = R.drawable.ic_play

    override fun getStopIcon() = R.drawable.ic_stop
}
