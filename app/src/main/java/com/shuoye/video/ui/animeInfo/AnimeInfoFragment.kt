package com.shuoye.video.ui.animeInfo

import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.jaeger.library.StatusBarUtil
import com.shuoye.video.R
import com.shuoye.video.VideoApplication
import com.shuoye.video.databinding.FragmentAnimeInfoBinding
import com.shuoye.video.ui.animeInfo.adapter.AnimeSeriesAdapter
import com.shuoye.video.utils.VideoUtils
import dagger.hilt.android.AndroidEntryPoint


/**
 * 番剧详情页面
 * @program Video
 * @ClassName AnimeInfoFragment
 * @author shuoye
 * @create 2021-10-24 21:55
 **/
@AndroidEntryPoint
class AnimeInfoFragment : Fragment() {
    private val viewModel: AnimeInfoViewModel by viewModels()
    private val args: AnimeInfoFragmentArgs by navArgs()
    private lateinit var binding: FragmentAnimeInfoBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAnimeInfoBinding.inflate(inflater, container, false)
        context ?: return binding.root
        init()
        return binding.root
    }

    fun init() {
        //设置状态栏全透明
        StatusBarUtil.setTransparent(activity)

        // 设置布局管理器
        binding.recycler.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        // 设置数据绑定
        viewModel.getAnime(args.animeInfoId).observe(viewLifecycleOwner, {
            binding.anime = it.data
            Glide.with(binding.root)
                .load(
                    VideoUtils.getSiteUrl(binding.anime?.animeInfo?.cover)
                )
                .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                .into(binding.cover)
            Glide.with(binding.root)
                .asBitmap()
                .load(VideoUtils.getSiteUrl(binding.anime?.animeInfo?.cover))
                //使用 Target.SIZE_ORIGINAL 作为目标
                .into(
                    object : SimpleTarget<Bitmap>() {
                        @RequiresApi(Build.VERSION_CODES.R)
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap?>?
                        ) {

                            // 通过Display获取实际屏幕宽高
                            val outSmallestSize = Point()
                            val outLargestSize = Point()
                            context?.display?.getCurrentSizeRange(outSmallestSize, outLargestSize)
                            val matrix = Matrix()
                            matrix.postScale(
                                outSmallestSize.x * 1.0f / resource.width,
                                outLargestSize.y * 1.0f / resource.height,
                            ) //长和宽放大缩小的比例
//                            matrix.postScale(10f, 10f)
                            val resizeBmp: Bitmap = Bitmap.createBitmap(
                                resource,
                                0,
                                0,
                                resource.width,
                                resource.height,
                                matrix,
                                true
                            )
                            val drawable = BitmapDrawable(Resources.getSystem(), resizeBmp)


                            val bitmapShader = BitmapShader(
                                drawable.bitmap,
                                Shader.TileMode.CLAMP,
                                Shader.TileMode.CLAMP
                            )
                            val linearGradient = LinearGradient(
                                0f,
                                0f,
                                0f,
                                binding.root.height * 1f,
                                resources.getColor(R.color.translucent,VideoApplication.getInstance().theme),
                                resources.getColor(R.color.transparent,VideoApplication.getInstance().theme),
                                Shader.TileMode.CLAMP
                            )

                            val composeShader = ComposeShader(
                                bitmapShader,
                                linearGradient,
                                PorterDuff.Mode.MULTIPLY
                            )
                            drawable.paint.shader = composeShader
                            binding.root.background = drawable    //设置背景
                        }
                    }
                )
            binding.recycler.adapter = it.data?.let { animeInfo -> AnimeSeriesAdapter(animeInfo) }
        })

        // 设置点击事件
        binding.play.setOnClickListener { view ->
            val action = AnimeInfoFragmentDirections.actionAnimeInfoFragmentToPlayerActivity(
                binding.anime!!.animeInfo.id,
                binding.anime!!.animeInfo.name
            )
            view.findNavController().navigate(action)
        }
    }
}