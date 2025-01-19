bool CUDAIsosurfaceVRScene::_renderCore()
{
	if (!SingleScalarVolumePrepare(m_Data)) return false;
	if (!IsosurfaceRayCasterPrepare(m_RayCaster)) return false;
	GenerateImage(m_RayProducer);
	SingleScalarVolumeClear();
	return true;
}

